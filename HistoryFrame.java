package bcz;
 
import javax.swing.*;
import java.util.ArrayList;
public class HistoryFrame extends JFrame {
    private JTextArea historyTextArea;
    public HistoryFrame(ArrayList<String> historyWords) {
        setSize(400, 300);
        setTitle("历史记录");
        setLayout(null);
        historyTextArea = new JTextArea();
        for (String word : historyWords) {
            historyTextArea.append(word + "\n");
        }
        JScrollPane scrollPane = new JScrollPane(historyTextArea);
        scrollPane.setBounds(10, 10, 380, 250);
        add(scrollPane);
    }
}