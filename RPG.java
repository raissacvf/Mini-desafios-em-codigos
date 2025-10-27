import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RPG_GUI extends JFrame {

    private JTextField dadoField, vidaJField, ataqueJField, defesaJField, vidaIField, ataqueIField;
    private JTextArea resultadoArea;
    private JButton simularButton;

    public RPG_GUI() {
        // Configurações da janela
        setTitle("Batalha RPG: Jogador vs Goblin");
        setSize(550, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Painel do Cabeçalho (Instrução e Botão)
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel instrucaoLabel = new JLabel("<html><div style='text-align: center;'>Insira os status nos campos e clique em 'Simular Batalha' para ver o resultado do combate.</div></html>", SwingConstants.CENTER);
        instrucaoLabel.setFont(new Font("SansSerif", Font.ITALIC, 11));
        
        simularButton = new JButton("Simular Batalha");
        simularButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simularCombate();
            }
        });

        headerPanel.add(instrucaoLabel, BorderLayout.NORTH);
        headerPanel.add(simularButton, BorderLayout.SOUTH);

        // Painel de Entrada de Dados (Central)
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        
        // Inicialização dos campos com valores padrão
        dadoField    = new JTextField("9"); 
        vidaJField   = new JTextField("100"); 
        ataqueJField = new JTextField("20"); 
        defesaJField = new JTextField("10"); 
        vidaIField   = new JTextField("50"); 
        ataqueIField = new JTextField("35"); 

        inputPanel.add(new JLabel("Dado (1-10):"));
        inputPanel.add(dadoField);
        inputPanel.add(new JLabel("Vida do Jogador:"));
        inputPanel.add(vidaJField);
        inputPanel.add(new JLabel("Ataque do Jogador:"));
        inputPanel.add(ataqueJField);
        inputPanel.add(new JLabel("Defesa do Jogador:"));
        inputPanel.add(defesaJField);
        inputPanel.add(new JLabel("Vida do Inimigo:"));
        inputPanel.add(vidaIField);
        inputPanel.add(new JLabel("Ataque do Inimigo:"));
        inputPanel.add(ataqueIField);

        // Área de Resultado (Inferior)
        resultadoArea = new JTextArea(10, 30);
        resultadoArea.setEditable(false);
        resultadoArea.setFont(new Font("Monospaced", Font.BOLD, 12));
        resultadoArea.setText("Resultado da Batalha...");
        
        JScrollPane scrollPane = new JScrollPane(resultadoArea);

        // Adiciona os painéis à janela principal
        add(headerPanel, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);
        
        setVisible(true);
    }

    /**
     * Lógica principal de simulação do combate.
     */
    private void simularCombate() {
        try {
            // Leitura dos valores
            int dados          = Integer.parseInt(dadoField.getText());
            int vidaDoJogador  = Integer.parseInt(vidaJField.getText());
            int ataqueDoJogador = Integer.parseInt(ataqueJField.getText());
            int defesaDoJogador = Integer.parseInt(defesaJField.getText());
            int vidaDoInimigo  = Integer.parseInt(vidaIField.getText());
            int ataqueDoInimigo = Integer.parseInt(ataqueIField.getText());

            // ---------------------------------------------------------------------
            // Lógica do RPG
            // ---------------------------------------------------------------------
            
            // 1. Cálculo do Modificador
            int modificador;
            // Regra do Crítico: dado = 9 dobra o valor.
            if (dados > 8 && dados < 10) {
                modificador = dados * 2;
            } else {
                modificador = dados;
            }

            // 2. Ataque do Inimigo
            // Dano Sofrido = Ataque Inimigo - (Defesa Jogador + Modificador)
            int danoSofrido = ataqueDoInimigo - (defesaDoJogador + modificador);

            // Dano mínimo é 0
            if (danoSofrido < 0) {
                danoSofrido = 0;
            }

            vidaDoJogador -= danoSofrido;
            
            // Log de eventos
            StringBuilder log = new StringBuilder();
            log.append("--- INÍCIO DA BATALHA ---\n");
            log.append(String.format("Dado: %d. Modificador: %d.\n", dados, modificador));
            log.append("--------------------------\n");
            log.append(String.format("DANO SOFRIDO:\n"));
            log.append(String.format("  -> Dano: %d\n", danoSofrido));
            log.append(String.format("  -> Vida Jogador após ataque: %d\n", vidaDoJogador > 0 ? vidaDoJogador : 0));
            log.append("--------------------------\n");

            // 3. Verifica sobrevivência e contra-ataque
            if (vidaDoJogador > 0) {
                
                // Contra-Ataque: Dano Jogador = Ataque Jogador + Modificador
                int danoDoJogador = ataqueDoJogador + modificador;
                
                vidaDoInimigo -= danoDoJogador;

                log.append(String.format("CONTRA-ATAQUE:\n"));
                log.append(String.format("  -> Dano: %d\n", danoDoJogador));
                log.append(String.format("  -> Vida Inimigo restante: %d\n", vidaDoInimigo > 0 ? vidaDoInimigo : 0));
                log.append("--------------------------\n");
                
                // Resultado Final
                if (vidaDoInimigo <= 0) {
                    log.append("\n<< JOGADOR SOBREVIVEU E DERROTOU O INIMIGO >>\n");
                } else {
                    log.append("\n<< JOGADOR SOBREVIVEU E NAO DERROTOU O INIMIGO >>\n");
                }

            } else {
                // Resultado Final: Morte
                log.append("\n<< JOGADOR NAO SOBREVIVEU >>\n");
            }
            
            resultadoArea.setText(log.toString());

        } catch (NumberFormatException ex) {
            // Tratamento de erro para entradas inválidas
            JOptionPane.showMessageDialog(this, "ERRO: Por favor, insira apenas números inteiros válidos em todos os campos.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
            resultadoArea.setText("Resultado da Batalha...");
        }
    }

    // Inicia a aplicação GUI
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RPG_GUI();
            }
        });
    }
}
