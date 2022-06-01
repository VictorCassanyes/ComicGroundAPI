package com.proyecto.security;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class AESEncriptacion {
	
	public String cifrar(String contraseña) {
		try {
			//Generar clave aleatoria usando el tipo de cifrado AES
			KeyGenerator keygen=KeyGenerator.getInstance("AES");
			SecretKey key=keygen.generateKey();
			
			//Crear cifrador e iniciarlo en modo cifrar
			Cipher cifrador=Cipher.getInstance("AES");
			cifrador.init(Cipher.ENCRYPT_MODE, key);
			byte[] contraseñaCifrada=cifrador.doFinal(contraseña.getBytes());
			String contraseñaCifradaS=Base64.getEncoder().encodeToString(contraseñaCifrada);
			
			return contraseñaCifradaS;
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
