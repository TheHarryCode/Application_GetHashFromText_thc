package com.harrymesa;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

public class AppHashFromText {
	// Atributos de la clase
	private String texto = "";
	private Algoritmo algoritmo = null;

	// Constantes
	private static final String VERSION = "1.0";

	/**
	 * Constructor que puede inicializar el algoritmo a utilizar
	 *
	 * @param texto
	 */
	public AppHashFromText(Algoritmo algoritmo) {
		this.algoritmo = algoritmo;
	}

	public AppHashFromText() {
	}

	/**
	 * Enumeracion de algoritmos soportados por la clase App
	 */
	public enum Algoritmo {
		// Declaracion del nombre de cada algoritmo
		MD2("MD2"), MD5("MD5"), SHA1("SHA-1"), SHA256("SHA-256"), SHA512(
				"SHA-512"), CRC32("CRC32");

		// nombre del algoritmo
		private String nombre;

		private Algoritmo(String nombre) {
			this.nombre = nombre;
		}

		private String getNombre() {
			return nombre;
		}
	}

	/**
	 * Getters an Setters de la clase
	 */
	public String getTexto() {
		return texto;
	}

	public Algoritmo getAlgoritmo() {
		return algoritmo;
	}

	public void setAlgoritmo(Algoritmo algoritmo) {
		this.algoritmo = algoritmo;
	}

	/**
	 * Genera el hash del texto definido en la clase
	 *
	 * @return hash
	 */

	public String generateHash(String texto) {

		this.texto = texto;
		// Variable que almacena el criptograma generado
		String hash = "";
		// Variable que guardara el digest generado
		byte[] digest = null;
		// Variable que obtiene el buffer del texto
		byte[] buffer = texto.getBytes();

		switch (algoritmo) {
		case MD2:
		case MD5:
		case SHA1:
		case SHA256:
		case SHA512:
			// Se intenta obtener el Message Digest del algoritmo
			// seleccionado. Esto es en base a la clase MessageDigest
			// del paquete Security de Java
			try {
				// Instancio un objeto MessageDigest con el algoritmo apropiado
				MessageDigest md = MessageDigest.getInstance(algoritmo
						.getNombre());
				// Reseteo el digest que pueda existir en el objeto
				md.reset();
				// Envio el buffer el mensaje a encriptar
				md.update(buffer);
				// Obtengo el Digest del Message
				digest = md.digest();
				// Obtengo la cadena del hash en valores hexadecimales
				hash = toHexadecimal(digest);
			} catch (NoSuchAlgorithmException e) {
				// Controlo el mensaje de cualquier excepcion generada
				e.printStackTrace();
			}
			break;

		case CRC32:
			// Obtengo el CRC32 del texto ingresado
			hash = getCRC32();
			break;
		}
		return hash;
	}




	/**
	 * Para los algoritmos que tienen hash, hace la conversion del arreglo de
	 * bytes[] generado a una cadena String
	 *
	 * @param digest
	 * @return hash
	 */
	private String toHexadecimal(byte[] digest) {
		String hash = "";
		for (byte aux : digest) {
			int b = aux & 0xff; // Hace un cast del byte a hexadecimal
			if (Integer.toHexString(b).length() == 1)
				hash += "0";
			hash += Integer.toHexString(b);
		}
		return hash;
	}

	/**
	 * Obtiene el Checksum 32 (CRC32) del texto ingresado
	 *
	 * @return
	 */
	private String getCRC32() {
		String hash = null;
		byte[] bytes = texto.getBytes();
		// Objeto Checksum
		Checksum crc32 = new CRC32();
		// Inicializo el objeto
		crc32.reset();
		// Actualizo la con el arreglo de bytes que obtengo del texto
		crc32.update(bytes, 0, bytes.length);
		hash = Long.toHexString(crc32.getValue()).toUpperCase();
		return hash;
	}

	public static void main( String[] args ) {

		// 1. Creacion del objeto Criptography :
		AppHashFromText ejemplo = new AppHashFromText();

		// Obteniendo version
		System.out.println(" [ Criptography " + ejemplo.VERSION	+ " ]\n");

		// 2. Seleccionar el algoritmo
		ejemplo.setAlgoritmo(Algoritmo.MD2);

		// 3. Obtener el hash
		System.out.println("\nHash : "
				+ ejemplo.generateHash("Esta es una prueba"));
		System.out.println("Algoritmo : " + ejemplo.getAlgoritmo());
		System.out.println("Texto : " + ejemplo.getTexto());

		// 4. Cambiar el algoritmo
		ejemplo.setAlgoritmo(Algoritmo.MD5);
		System.out.println("\nHash : "
				+ ejemplo.generateHash("Esta es una prueba"));
		System.out.println("Algoritmo : " + ejemplo.getAlgoritmo());
		System.out.println("Texto : " + ejemplo.getTexto());

		// 5. Cambiar el texto
		ejemplo.setAlgoritmo(Algoritmo.SHA256);
		System.out.println("\nHash : "
				+ ejemplo.generateHash("Codes and Tags in other Hash"));
		System.out.println("Algoritmo : " + ejemplo.getAlgoritmo());
		System.out.println("Texto : " + ejemplo.getTexto());

		// Y otro algoritmo....
		ejemplo.setAlgoritmo(Algoritmo.CRC32);
		System.out
		.println("\nHash : " + ejemplo.generateHash("Utilizando CRC32"));
		System.out.println("Algoritmo : " + ejemplo.getAlgoritmo());
		System.out.println("Texto : " + ejemplo.getTexto());



	}

}
