package pos.presentation;

import pos.data.Database;
import pos.data.UserDao;
import pos.logic.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JDialog{
    private JPanel panel;
    private JPanel loginPanel;
    private JLabel idLabel;
    private JTextField idTxt;
    private JLabel claveLabel;
    private JButton loginButton;
    private JPanel buttonPanel;
    private JPasswordField claveTxt;

    public Login(JFrame frame)
    {
        super(frame);
        setTitle("Login");
        setContentPane(panel);
        setMinimumSize(new Dimension(270, 190));
        setModal(true);
        setLocationRelativeTo(frame);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idTxt.getText();
                String clave = claveTxt.getText();

                user = getAuthenticateUser(id, clave);

                if (user != null)
                {
                    dispose();
                }
                else
                {
                    JOptionPane.showMessageDialog(Login.this, "ID O CLAVE INVALIDA", "INTENTE DE NUEVO", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setVisible(true);
    }

    public User user;

    private User getAuthenticateUser(String id, String clave)
    {
        UserDao userDao = new UserDao(Database.instance()); // Asegúrate de tener una clase UserDao para manejar la lógica de datos de User.
        try {
            user = userDao.findUserByIdAndPassword(id, clave);
            return user;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al conectarse con la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return null;
    }
}
