public class FixedDepositDialog extends JDialog {
    private CuteTextField depositAmountField;  // 存款金额字段
    private CuteTextField depositDurationField;  // 存款期限字段
    private Button createButton;  // 创建按钮
    private JPanel contentPanel;  // 内容面板
    private CuteLabel headerLabel;  // 标题标签

    private List<ActionListener> listeners = new ArrayList<>();  // 监听器列表

    public void addFixedDepositListener(ActionListener listener) {
        listeners.add(listener);  // 添加定期存款监听器
    }

    private void notifyFixedDepositCreated() {
        ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "FixedDepositCreated");
        for (ActionListener listener : listeners) {
            listener.actionPerformed(event);  // 通知所有监听器定期存款已创建
        }
    }

    public FixedDepositDialog(Frame owner, User currentUser) {
        super(owner, "Create Fixed Deposit", true);  // 创建定期存款对话框
        setSize(700, 300);  // 设置尺寸
        setLocationRelativeTo(owner);  // 相对于父窗口居中
        getContentPane().setLayout(new BorderLayout(10, 10));  // 设置布局

        // 标题面板
        headerLabel = new CuteLabel("New Fixed Deposit");  // 新的定期存款标题
        getContentPane().add(headerLabel, BorderLayout.NORTH);

        // 主内容面板
        contentPanel =  new JPanel();
        contentPanel.setLayout(new GridLayout(0, 2, 10, 5));  // 动态行，2列
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));  // 设置边框

        // 添加金额标签和字段
        CuteLabel amountLabel = new CuteLabel("Amount:");  // 金额标签
        depositAmountField = new CuteTextField(10);  // 金额输入字段
        contentPanel.add(amountLabel);  // 添加金额标签到内容面板
        contentPanel.add(depositAmountField);  // 添加金额字段到内容面板

        // 添加期限标签和字段
        CuteLabel durationLabel = new CuteLabel("Duration (months):");  // 期限标签
        depositDurationField =  new CuteTextField(10);  // 期限输入字段
        contentPanel.add(durationLabel);  // 添加期限标签到内容面板
        contentPanel.add(depositDurationField);  // 添加期限字段到内容面板

        getContentPane().add(contentPanel, BorderLayout.CENTER);  // 将内容面板添加到中心区域

        // 按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));  // 创建按钮面板
        createButton = new Button("Create Deposit");  // 创建存款按钮
        createButton.addActionListener(e -> createFixedDeposit(currentUser));  // 添加按钮监听器
        buttonPanel.add(createButton);  // 将按钮添加到按钮面板

        getContentPane().add(buttonPanel, BorderLayout.SOUTH);  // 将按钮面板添加到南部区域

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);  // 设置默认关闭操作
    }

    private void createFixedDeposit(User currentUser) {
        try {
            double amount = Double.parseDouble(depositAmountField.getText());  // 获取输入的金额
            int duration = Integer.parseInt(depositDurationField.getText());  // 获取输入的期限

            if (amount <= 0 || duration <= 0) {
                JOptionPane.showMessageDialog(this, "Amount and duration must be positive numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;  // 显示错误信息，如果金额或期限为非正数
            }

            LocalDate startDate = LocalDate.now();  // 获取当前日期作为开始日期
            LocalDate endDate = startDate.plusMonths(duration);  // 计算结束日期
            double annualInterestRate = 0.05;  // 假设固定年利率为5%

            FixedDeposit newDeposit = new FixedDeposit(currentUser.getUid(), amount, annualInterestRate, startDate, endDate);
            XMLDBManager.addFixedDeposit(newDeposit);  // 添加新的定期存款

            JOptionPane.showMessageDialog(this, "Fixed Deposit Created Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            notifyFixedDepositCreated();  // 通知监听器定期存款已创建
            dispose();  // 关闭对话框

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);  // 显示输入错误信息
        }
    }
}
