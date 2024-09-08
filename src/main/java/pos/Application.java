package pos;

import pos.logic.Service;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Application
{
    public static pos.presentation.clientes.Controller clientesController;
    public static pos.presentation.cajeros.Controller cajerosController;
    public static pos.presentation.productos.Controller productosController;
    public static pos.presentation.facturar.Controller facturarController;
    public static pos.presentation.historico.Controller historicoController;
    public static pos.presentation.estadistica.Controller estadisticaController;

    public static JFrame window;

    public final static int MODE_CREATE=1;
    public final static int MODE_EDIT=2;

    public static Border BORDER_ERROR = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.RED);

    public static void main(String[] args)
    {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        }
        catch (Exception ex) {};

        window = new JFrame();
        JTabbedPane tabbedPane = new JTabbedPane();
        window.setContentPane(tabbedPane);

        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                Service.getInstance().stop();
            }
        });


        // Facturar
        pos.presentation.facturar.Model facturarModel = new pos.presentation.facturar.Model();
        pos.presentation.facturar.View facturarView = new pos.presentation.facturar.View();
        facturarController = new pos.presentation.facturar.Controller(facturarView, facturarModel,productosController);

        //Clientes
        pos.presentation.clientes.Model clientesModel = new pos.presentation.clientes.Model();
        pos.presentation.clientes.View clientesView = new pos.presentation.clientes.View();
        clientesController = new pos.presentation.clientes.Controller(clientesView, clientesModel);

        //Cajeros
        pos.presentation.cajeros.Model cajerosModel = new pos.presentation.cajeros.Model();
        pos.presentation.cajeros.View cajerosView = new pos.presentation.cajeros.View();
        cajerosController = new pos.presentation.cajeros.Controller(cajerosView, cajerosModel);

        //Productos
        pos.presentation.productos.Model productosModel = new pos.presentation.productos.Model();
        pos.presentation.productos.View productosView = new pos.presentation.productos.View();
        productosController = new pos.presentation.productos.Controller(productosView, productosModel);

        //Historico
        pos.presentation.historico.Model historicoModel = new pos.presentation.historico.Model();
        pos.presentation.historico.View historicoView = new pos.presentation.historico.View();
        historicoController = new pos.presentation.historico.Controller(historicoView, historicoModel);

        //Estadisticas
        pos.presentation.estadistica.Model estadisticaModel = new pos.presentation.estadistica.Model();
        pos.presentation.estadistica.View estadisticaView = new pos.presentation.estadistica.View();
        estadisticaController = new pos.presentation.estadistica.Controller(estadisticaView, estadisticaModel);

        facturarView.initialize(tabbedPane,cajerosModel,clientesModel);
        clientesView.initialize(tabbedPane);
        cajerosView.initialize(tabbedPane);
        productosView.initialize(tabbedPane);
        historicoView.initialize(tabbedPane);
        estadisticaView.initialize(tabbedPane);


        window.setSize(790,520);
        window.setResizable(false);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setIconImage((new ImageIcon(Application.class.getResource("presentation/icons/icon.png"))).getImage());
        window.setTitle("POS: Point Of Sale");
        window.setLocationRelativeTo(null);
        window.setVisible(true);


    }
}
