package util;


import accesodatos.Conexion;
/*
import java.awt.Frame;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.type.OrientationEnum;
import net.sf.jasperreports.swing.JRViewer;
import net.sf.jasperreports.view.JasperViewer;
*/

public class Reportes extends Conexion {
    
/*
    public static final String RUTA_REPORTES = System.getProperties().getProperty("user.dir") + "/src/reportes/";

    public JasperViewer reporteInterno(String archivoReporte,Map<String,Object> parametros) throws Exception {
        try {
            //URL rutaR = new URL(getClass().getResource("/reportes/"+archivoReporte).toString());
            //JasperPrint reporte = JasperFillManager.fillReport(rutaR.getPath(), parametros, this.abrirConexion());
            
            
            System.out.println("ruta:" + RUTA_REPORTES+ archivoReporte);
            
            JasperPrint reporte = JasperFillManager.fillReport(Reportes.RUTA_REPORTES + archivoReporte, parametros, this.abrirConexion());

            JasperViewer visor = new JasperViewer(reporte, false);

            visor.setExtendedState(JasperViewer.MAXIMIZED_BOTH);
            visor.setTitle(archivoReporte);

            return visor;
        } catch (JRException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());

        }
        return null;
    }

//    public JasperViewer reporteInternoHorizontal(String archivoReporte) throws Exception {
//        try {
//            //URL rutaR = new URL(getClass().getResource("/reportes/"+archivoReporte).toString());
//            //JasperPrint reporte = JasperFillManager.fillReport(rutaR.getPath(), parametros, this.abrirConexion());
//            JasperPrint reporte = JasperFillManager.fillReport(Reportes.RUTA_REPORTES + archivoReporte, null, this.abrirConexion());
//            JasperViewer visor = new JasperViewer(reporte, false);
//
//            //OrientationEnum.LANDSCAPE = Horizontal
//            reporte.setOrientation(OrientationEnum.LANDSCAPE);            
//            visor.setExtendedState(JasperViewer.MAXIMIZED_BOTH);
//            visor.setTitle(archivoReporte);
//            
//            return visor;
//        } catch (JRException e) {
//            JOptionPane.showMessageDialog(null, e.getMessage());
//        }
//        return null;
//    }
    
    public JasperViewer reporte1Parametros(String archivoReporte, Object parametro1, Object jsParametro1) throws Exception {
        try {
            Map parametros = new HashMap();
            parametros.put(jsParametro1, parametro1);

            JasperPrint reporte = JasperFillManager.fillReport(Reportes.RUTA_REPORTES + archivoReporte, parametros, this.abrirConexion());

            JasperViewer visor = new JasperViewer(reporte, false);

            visor.setExtendedState(JasperViewer.MAXIMIZED_BOTH);
            visor.setTitle(archivoReporte);

            return visor;
        } catch (JRException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());

        }
        return null;
    }

    public JasperViewer reporte2Parametros(String archivoReporte, Object parametro1, Object jsParametro1, Object parametro2, Object jsParametro2) throws Exception {
        try {
            Map parametros = new HashMap();
            parametros.put(jsParametro1, parametro1);
            parametros.put(jsParametro2, parametro2);

            JasperPrint reporte = JasperFillManager.fillReport(Reportes.RUTA_REPORTES + archivoReporte, parametros, this.abrirConexion());

            JasperViewer visor = new JasperViewer(reporte, false);

            visor.setExtendedState(JasperViewer.MAXIMIZED_BOTH);
            visor.setTitle(archivoReporte);

            return visor;
        } catch (JRException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());

        }
        return null;
    }

    public JasperViewer reporte3Parametros(String archivoReporte, Object parametro1, Object jsParametro1, Object parametro2, Object jsParametro2, Object parametro3, Object jsParametro3) throws Exception {
        try {
            Map parametros = new HashMap();
            parametros.put(jsParametro1, parametro1);
            parametros.put(jsParametro2, parametro2);
            parametros.put(jsParametro3, parametro3);

            JasperPrint reporte = JasperFillManager.fillReport(Reportes.RUTA_REPORTES + archivoReporte, parametros, this.abrirConexion());

            JasperViewer visor = new JasperViewer(reporte, false);
            visor.setExtendedState(JasperViewer.MAXIMIZED_BOTH);
            visor.setTitle(archivoReporte);

            return visor;
        } catch (JRException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());

        }
        return null;
    }
    */
}
