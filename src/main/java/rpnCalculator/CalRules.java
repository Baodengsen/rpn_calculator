package rpnCalculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class CalRules {

    /**
     * 封装一元运算符计算法则,可在该方法中增加操作符功能
     * @param stk1 操作符栈
     * @param stk1 日志栈
     * @param opt  操作符
     */
    public void unaryOptRules(Stack<Double> stk1, Stack<List<Double>> stk2, String opt) throws Exception {
        double num = stk1.pop();
        switch (opt) {
            case "sqrt": // 操作符为sqrt
                stk1.push(sqrt(num));
                stk2.push(getStack(stk1));
                break;
            default:
                throw new Exception("ERROR");
        }
    }

    /**
     * 封装二元运算符计算法则，可在此方法中增加更多操作符，实现扩展。
     * @param stk1 操作符栈
     * @param stk1 日志栈
     * @param opt  操作符
     */
    public void binaryOptRules(Stack<Double> stk1, Stack<List<Double>> stk2, String opt) throws Exception {
        double num2 = stk1.pop(); // 取出操作数栈顶数值
        double num1 = stk1.pop(); // 取出操作数栈顶数值
        switch (opt) {
            case "+":
                stk1.push(num1 + num2); // 计算并将结果入栈
                stk2.push(getStack(stk1)); // 同时日志栈记录操作数栈的数据
                break;
            case "-":
                stk1.push(num1 - num2);
                stk2.push(getStack(stk1));
                break;
            case "*":
                stk1.push(num1 * num2);
                stk2.push(getStack(stk1));
                break;
            case "/":
                stk1.push(div(num1, num2));
                stk2.push(getStack(stk1));
                break;
            default:
                throw new Exception("ERROR");
        }
    }

    /**
     * 封装功能操作符undo、clear的计算法则
     * @param stk1 操作数栈
     * @param stk2 日志栈
     * @param opt  操作符
     */
    public void funcRules(Stack<Double> stk1, Stack<List<Double>> stk2, String opt) throws Exception {
        switch (opt) {
            case "undo": // undo的情况
                while (!stk1.empty()) { // 操作数栈清空
                    stk1.pop();
                }
                if (!stk2.empty()) { // 如果日志栈不为空，将栈顶数据弹出
                    stk2.pop();
                    if (!stk2.empty()) { // 弹出数据后，日志栈不为空，将现在的栈顶数据压入操作数栈
                        List<Double> list1 = stk2.peek(); // 读取日志栈顶数据，并将其存放到list1集合中
                        for (int i = 0; i < list1.size(); i++) { // 将现在的栈顶数据压入操作数栈
                            if (list1.get(i) != null) {
                                stk1.push(list1.get(i));
                            }
                        }
                    }
                }
                break;
            case "clear": // clear的情况
                while (!stk1.empty()) { // 清空操作数栈
                    stk1.pop();
                }
                List<Double> list2 = new ArrayList<>(); // 将null压入日志栈，以便执行undo时可以区别
                list2.add(null);
                stk2.push(list2);
                break;
            default:
                throw new Exception("ERROR");
        }
    }

    /**
     * 除法计算法则
     * @param a 操作数1
     * @param b 操作数2
     */
    private double div(double a, double b) throws Exception {
        if (b == 0) {
            throw new Exception("除数不能为0!");
        }
        return a / b;
    }

    /**
     * 开平方计算法则
     * @param f
     */
    private double sqrt(double f) throws Exception {
        if (f < 0) {
            throw new Exception("不能对负数开平方！");
        }
        double a = (double) Math.sqrt(f);
        return a;
    }

    /**
     * 该方法获取栈中数据，将其存在List集合中
     * @param stk
     */
    public List<Double> getStack(Stack<Double> stk) {
        List<Double> getStk = new ArrayList<>();
        for (Double x : stk) {
            getStk.add(x);
        }
        return getStk;
    }
}
