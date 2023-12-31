package bcz;
 
//导入需要用到的Java类库
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
 
public class Bczh extends JFrame implements ActionListener {
    /**
     *
     */
    // 定义所有的单词列表ArrayList
    ArrayList<String> totalWords = new ArrayList<String>();
    // 定义用来测试的单词列表ArrayList
    ArrayList<String> testWords = new ArrayList<String>();
    //定义历史记录
    ArrayList<String> historyWords = new ArrayList<String>();
    JLabel scoreLabel = new JLabel(); //定义标签用来显示答对单词数
    JLabel wordLabel = new JLabel(); //定义标签用来显示正在测试的单词
    JLabel historyLabel = new JLabel();//  定义标签用来显示历史单词
    ButtonGroup group = new ButtonGroup();//定义单选按钮框
    JRadioButton rb1 = new JRadioButton();//定义选项一单选按钮
    JRadioButton rb2 = new JRadioButton();//定义选项二单选按钮
    JRadioButton rb3 = new JRadioButton();//定义选项三单选按钮
    JRadioButton rb4 = new JRadioButton();//定义选项四单选按钮
    JLabel answerLabel = new JLabel(); //定义标签显示正确答案
    // 定义“继续”和“结束”按钮
    JButton continueButton = new JButton("下一题");
    JButton endButton = new JButton("结束");
    JButton historyButton=new JButton("历史记录");
    // 定义变量存储当前单词、正确答案和四个选项
    String Englishword, answer;
    String[] item = new String[4];
    int totalNum;//定义变量记录测试的单词总数
    int okNum = 0; //定义变量记录答对的单词数
    int index; //定义变量记录当前的单词在测试单词表中的序号
 
    Bczh() throws FileNotFoundException, IOException {
        this.setTitle("百词斩");//设置窗口标题
        this.setSize(500, 600);//设置窗口大小
        this.setLayout(null);//设置窗口布局
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置关闭方式
        //设置答对的单词数的颜色，字体，位置
        scoreLabel.setForeground(Color.red);//设置颜色
        Font font = new Font("宋体", Font.PLAIN, 24);
        scoreLabel.setFont(font); //设置字体
        scoreLabel.setBounds(200, 20, 300, 30);//设置位置
 
        //设置正在测试的英语单词的字体和位置
        wordLabel.setFont(font);//设置字体
        wordLabel.setBounds(40, 80, 200, 30);//设置位置
 
        //设置历史记录的单词的字体和位置
        historyLabel.setFont(font);
        historyLabel.setBounds(40, 140, 200, 30);
 
        rb1.setFont(font);//设置选项一的字体
        rb2.setFont(font);//设置选项二的字体
        rb3.setFont(font);//设置选项三的字体
        rb4.setFont(font);//设置选项四的字体
        rb1.setBounds(40, 120, 350, 50);//设置选项一的位置
        rb2.setBounds(40, 170, 350, 50);//设置选项二的位置
        rb3.setBounds(40, 220, 350, 50);//设置选项三的位置
        rb4.setBounds(40, 270, 350, 50);//设置选项四的位置
        // 添加动作命令标识
        rb1.setActionCommand("1");//设置选项一的动作指令
        rb2.setActionCommand("2");//设置选项二的动作指令
        rb3.setActionCommand("3");//设置选项三的动作指令
        rb4.setActionCommand("4");//设置选项四的动作指令
 
        //将各个单选按钮加入到按钮组，确保多选一（一个按钮组内只能选一项）
        group.add(rb1);
        group.add(rb2);
        group.add(rb3);
        group.add(rb4);
        //给四个选项按钮添加监听器
        rb1.addActionListener(this);
        rb2.addActionListener(this);
        rb3.addActionListener(this);
        rb4.addActionListener(this);
 
        //设置答案标签
        answerLabel.setBounds(50, 350, 350, 50);//设置答案标签的位置
        answerLabel.setFont(font);//设置答案标签的字体
        answerLabel.setForeground(Color.red);//设置答案的文字颜色
 
        //设置继续按钮
        continueButton.setFont(font);//设置继续按钮的字体
        continueButton.setBounds(50, 450, 120, 30);//设置继续按钮的位置
        continueButton.setActionCommand("continue");
        continueButton.addActionListener(this);//添加监听
        continueButton.setEnabled(false); //初始状态下禁用继续按钮
 
        //设置结束按钮
        endButton.setFont(font);//设置结束按钮的字体
        endButton.setBounds(200, 450, 120, 30);//设置结束按钮位置
        endButton.setActionCommand("end");
        endButton.addActionListener(this); // 添加监听
 
        //设置历史记录按钮
        historyButton.setFont(font);//设置历史记录的按钮
        historyButton.setBounds(350,450,120,30);
        historyButton.setActionCommand("history");
        historyButton.addActionListener(this);
        historyButton.setEnabled(false);//  将历史记录按钮初始化为不可用
 
        //将各组件添加到窗口中
        this.add(scoreLabel);
        this.add(wordLabel);
        this.add(rb1);
        this.add(rb2);
        this.add(rb3);
        this.add(rb4);
        this.add(continueButton);
        this.add(endButton);
        this.add(historyButton);
        this.add(answerLabel);
 
        this.setVisible(true); //设置窗口可见
        this.setLocationRelativeTo(null);//设置窗口位置，居中显示
 
        //从文件加载单词列表
 
        //根据用户需求，选择不同级别的单词
        Object[] options = {"四级", "六级"};
        int n = JOptionPane.showOptionDialog(this, "请选择单词级别", "选择", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        String fileName = null;
        if (n == 0) {
            fileName = "Word.txt";
            loadWords(fileName);
        } else if (n == 1) {
            fileName = "words.txt";
            loadWords(fileName);
        }
try {
        loadWords(fileName);
        //选择测试单词
        selectTestWords(10);
        //显示第一个单词
        displayNextWord();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
 
    //加载单词列表
    private void loadWords(String string) throws FileNotFoundException,IOException{
        // 使用BufferedReader按行读取文本文件中的数据，将每一行存储为ArrayList中的一个元素
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(string),"UTF-8"));
        String line;
        // 加载词库
        while ((line = reader.readLine()) != null) {
            totalWords.add(line);}
    }
 
    //从单词列表选择测试单词
    private void selectTestWords(int num) {
        //打乱单词集合中的顺序，并从中随机选择num个单词作为测试单词
        Collections.shuffle(totalWords);
        testWords.addAll(totalWords.subList(0, num));
        totalNum = num;//记录测试的单词总数
        scoreLabel.setText("正确个数："+okNum+"/"+totalNum);//初始化正确个数和测试总数
    }
 
    //显示下一个单词和选项
    private void displayNextWord() {
        if (index < testWords.size()) {
            String wordInfo = testWords.get(index);//获取当前测试的单词信息（包含单词和正确答案）
            String[] parts = wordInfo.split(" ");//把单词和正确答案分开
            Englishword = parts[0];//获取单词
            answer = parts[1];//获取正确答案
            wordLabel.setText(Englishword);//显示单词
            ArrayList<String> options = generateOptions(index);//生成选项列表
            Collections.shuffle(options);//打乱选项顺序
 
            //把选项显示在四个选项按钮上
            rb1.setText(options.get(0));
            rb2.setText(options.get(1));
            rb3.setText(options.get(2));
            rb4.setText(options.get(3));
            answerLabel.setText("");//隐藏正确答案的标签
            group.clearSelection();//取消选择的选项
            rb1.setEnabled(true);//启用所有选项
            rb2.setEnabled(true);
            rb3.setEnabled(true);
            rb4.setEnabled(true);
            continueButton.setEnabled(false);//禁用继续按钮
 
        } else {
            //所有单词都测试完毕
            JOptionPane.showMessageDialog(this, "测试完成！答对了 " + okNum + " 个单词。");
            continueButton.setEnabled(false);//禁用继续按钮
            endButton.setEnabled(false);//禁用结束按钮
            historyButton.setEnabled(true);
        }
    }
 
    // 生成选项列表
    private ArrayList<String> generateOptions(int currentIndex) {
        ArrayList<String> options = new ArrayList<String>();
        options.add(answer);//把正确答案添加到选项列表中
        while (options.size() < 4) {//如果选项数量小于4
            int randomIndex = (int) (Math.random() * totalWords.size());//随机生成一个单词下标
            String option = totalWords.get(randomIndex).split(" ")[1];//获取该单词的正确答案
            if (!options.contains(option) && randomIndex != currentIndex) {//如果选项列表中不包含该选项,且不和正确答案相同
                options.add(option);//把该选项添加到选项列表中
            }
        }
        return options;//返回选项列表
    }
 
    //处理按钮点击事件
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();//获取动作命令
        if (command.equals("continue")) {//如果点击继续按钮
            //index++;//当前测试单词下标加1 //如果加入这一行,所测单词如果答对,会减少测试数量,而不加入,没回答正确则正确答案会重复出现
            displayNextWord();//显示下一个单词
        } else if (command.equals("end")) {//如果点击结束按钮
            System.exit(0);//退出程序
        } else if(command.equals("history")){
            historyLabel.setText(historyWords.toString());//显示历史记录列表
            HistoryFrame historyFrame = new HistoryFrame(historyWords); // 创建一个 HistoryFrame 实例
            historyFrame.setVisible(true); // 将该实例设置为可见状态
        }
        else {//如果点击四个选项按钮
            if (group.getSelection() != null) {//判断是否已选择答案
                //禁用所有选项
                rb1.setEnabled(false);
                rb2.setEnabled(false);
                rb3.setEnabled(false);
                rb4.setEnabled(false);
                String selectedOption = group.getSelection().getActionCommand();//获取选中的答案
                String selectedAnswer = getSelectedAnswer(selectedOption);
                if (selectedAnswer.equals(answer)) {//如果答对了
                    okNum++;//答对单词数加1
                    scoreLabel.setText("答对的单词数：" + okNum + "/" + totalNum);//更新答对单词数标签
                    historyWords.add(Englishword+ " "+selectedAnswer);//  添加到历史记录列表中
                    testWords.remove(index);//从待测试单词列表中移除当前测试单词
                    answerLabel.setText("回答正确");
                } else {//如果答错了
                    answerLabel.setText("回答错误，正确答案是：" + answer);//显示正确答案
                    // 进入下一个单词判断
                    continueButton.setEnabled(true);
                    historyButton.setEnabled(true);
                }
                continueButton.setEnabled(true);//启用继续按钮
                historyButton.setEnabled(true);// 启用历史记录按钮
            }
        }
    }
 
    // 获取选中的答案
    private String getSelectedAnswer(String option) {
        if (option.equals("1")) {
 
            //一次答对的单词，以后不会再出现。
            if (rb1.getText().equals(answer)) {
                testWords.remove(index);
            }
 
            return rb1.getText();
        } else if (option.equals("2")) {
 
            //一次答对的单词，以后不会再出现。
            if (rb2.getText().equals(answer)) {
                testWords.remove(index);
            }
 
            return rb2.getText();
        } else if (option.equals("3")) {
 
            //一次答对的单词，以后不会再出现。
            if (rb3.getText().equals(answer)) {
                testWords.remove(index);
            }
 
            return rb3.getText();
        } else if (option.equals("4")) {
 
            //一次答对的单词，以后不会再出现。
            if (rb4.getText().equals(answer)) {
                testWords.remove(index);
            }
            return rb4.getText();
        } else {
            return "";
        }
    }
 
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {//SwingUtilities.invokeLater()方法可确保在Swing GUI线程中运行该程序，从而确保了线程安全。
            public void run() {
                try {
                    Bczh bczh= new Bczh();
                    bczh.setVisible(true);
                    bczh.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}