package myCalculator;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class CalForResult {
    private Stack<Double> numbers = new Stack<Double>(); // 操作数栈
    private Stack<List<Double>> numlogs = new Stack<>(); // 操作数栈的日志栈,用来存放操作数栈的历史记录
    /**
     * 该方法对RPN表达式进行计算
     *
     * @param rpn 为用户输入的RPN表达式
     */
    public void calRpnExpression(String rpn) throws Exception {
        String[] arpn = rpn.split(" "); // 以空格来分割rpn字符串成为字符串数组
        int i = 0;
        int apLength = arpn.length; // 获取表达式字符串数组的长度
        while (i < apLength) { // 该循环开始操作符计算
            CalRules cr = new CalRules(); // 创建计算法则类的对象，以便后面的引用
            int n = numbers.size(); // 获取栈的长度
            if (strToDigit(arpn[i]) != null) { // 字符串是操作数，则直接入栈
                numbers.push(strToDigit(arpn[i]));
                numlogs.push(getStack(numbers)); // 日志栈记录操作数栈的数据变化
            } else { // 字符串不是操作数，则要判断是哪种操作符
                String opt = arpn[i];
                if ("undo".equals(opt) || "clear".equals(opt)) { // 操作符是功能符undo或clear
                    cr.funcRules(numbers, numlogs, opt);
                } else if ("sqrt".equals(opt)) { // 操作符是一元运算符sqrt
                    if (n > 0) { // 如果栈中有操作数，则进行单目运算
                        cr.unaryOptRules(numbers, numlogs, opt);
                    } else { // 栈中没有操作数，输出提示信息，并跳出循环
                        System.out.print("operator" + opt + "(position:" + (2 * i - 1) + "):insufficient parameters ");
                        break;
                    }

                } else if ("+".equals(opt) || "-".equals(opt) || "*".equals(opt) || "/".equals(opt)) { // 操作符是二元运算符
                    if (n > 1) { // 栈的操作数大于等于2，则进行双目运算
                        cr.binaryOptRules(numbers, numlogs, opt);
                    } else { // 栈中没有操作数，输出提示信息，并跳出循环
                        System.out.print("operator" + opt + "(position:" + (2 * i + 1) + "):insufficient parameters ");
                        break;
                    }
                } else {
                    throw new Exception("输入的RPN表达式不合法！");
                }
            }
            i++;
        }
        displayStack(numbers);
    }

    /**
     * 该方法将字符串转换为数字类型Double
     *
     * @param str
     */
    private Double strToDigit(String str) {
        try {
            double num = Double.valueOf(str);
            return num;
        } catch (Exception e) { // 出现异常，则str字符串不是操作数
            return null;
        }
    }

    /**
     * 该方法获取栈中数据，将其存在List集合中
     *
     * @param stk
     */
    public List<Double> getStack(Stack<Double> stk) {
        List<Double> getStk = new ArrayList<>();
        for (Double x : stk) {
            getStk.add(x);
        }
        return getStk;
    }

    /**
     * 该方法将栈中的数据显示出来，从底层开始
     *
     * @param stk
     */
    public void displayStack(Stack<Double> stk) {
        if (stk.size() != 0) {
            System.out.print("stack:");
            for (Double x : stk) {
                System.out.print(outputFormat(x) + " ");
            }
        } else {
            System.out.println("stack:");
        }
        System.out.println();
    }

    /**
     * 该方法设置运算结果的显示格式，最多显示10位精度
     *
     * @param value  运算结果
     */
    public String outputFormat(double value) {
        DecimalFormat numformat = new DecimalFormat("##########.##########");
        String output = numformat.format(value);
        return output;
    }

    public static void main(String[] args) {
        CalForResult cf = new CalForResult();
        try {
            while (true) {
                System.out.println("请输入逆波兰表达式：");
                Scanner scan = new Scanner(System.in);
                String rpn = scan.nextLine();
                cf.calRpnExpression(rpn);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
