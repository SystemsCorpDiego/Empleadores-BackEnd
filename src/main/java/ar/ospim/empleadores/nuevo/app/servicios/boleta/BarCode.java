package ar.ospim.empleadores.nuevo.app.servicios.boleta;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import lombok.extern.slf4j.Slf4j;
import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;

@Slf4j
public class BarCode {
	
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
			log.error("No se pudo generar el codigo de barras", e);
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				log.error("No se pudo cerrar el stream", e);
			}
		}
		return null;
	}
}
