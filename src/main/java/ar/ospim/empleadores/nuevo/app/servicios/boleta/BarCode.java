package ar.ospim.empleadores.nuevo.app.servicios.boleta;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;

public class BarCode {
	//private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private static Logger logger = LoggerFactory.getLogger(BarCode.class);
	
	public static Image generarCodigoBarras(String numero) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			StringBuilder sb = new StringBuilder();
			int verif = 0;
			sb.append(numero);
			for (int i = 0; i < numero.length(); i++) {
				verif += Integer.parseInt(String.valueOf(numero.charAt(i)));
			}

			verif = verif * 13;
			// 2 ultimas verificador
			sb.append((verif) % 7);
			Barcode barCode = BarcodeFactory.createCode128B(sb.toString());
			barCode.setBarWidth(2);
			barCode.setBarHeight(1);
			barCode.setDrawingText(true);
			BarcodeImageHandler.writeJPEG(barCode, out);
			Image image = Toolkit.getDefaultToolkit().createImage(
					out.toByteArray());
			image.getWidth(null);
			image.getHeight(null);

			return image;
		} catch (Exception e) {
			logger.error("No se pudo generar el codigo de barras", e);
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				logger.error("No se pudo cerrar el stream", e);
			}
		}
		return null;
	}
}
