package com.sds.n3dx.conversion.worker;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.function.Predicate;

public class FileUtils {
	
	private static final String HEADER_SYNTHETIC = "[N3DX_SYNTHETIC]";
	private static final String SYMBOLIC_LINK_FALLBACK_HEADER = "[N3DX_LINK]";

	public static Path createSymbolicLink(final Path link, final Path target, final Predicate<Exception> supportsSymbolicLinkFallback) {
		try {
			Files.createDirectories(link.getParent());
			if (Files.isSymbolicLink(link) || isSymbolicFallback(link)) {
				// 이미 동일 파일이 존재한다면 재생성 필요 없음.
			    if (Files.exists(link) && Files.isSameFile(link, target)) {
			    	return link;
			    }

			    Files.deleteIfExists(link);
			}
		} catch (final IOException e) {
		     
	    }
		return null;
	}
	
	public static String getFileExtension(final Path file) {
		return null;
	}
	
	public static boolean supportsSymbolicLinkFallback(final Path link, final Path target, final Exception e) {
//		if (!N3dxExchangeConversionSettings.isSymbolickLinkFallbackEnabled()) {
//			return false;
//		}

		// TODO NP3DP-2990 windows에서 NAS 연결 시 프로토콜에 따라 symbolic link 생성이 불가능한 경우가 존재함
		// 임시 해결책으로 예외적으로 windows 전용인 skp 파일에 대해 txt 형식의 link 파일 생성.
		// NP3DP-6327 FileSystemException이 발생하는 경우가 존재하여 에러 유형 변경.
		return (e instanceof FileSystemException) && "skp".equalsIgnoreCase(getFileExtension(link.getFileName()));
	}
	
	public static boolean isSymbolicFallback(final Path path) {
		if (!Files.exists(path, LinkOption.NOFOLLOW_LINKS)) {
			return false;
		}

		try (BufferedReader br = Files.newBufferedReader(path)) {
			final int len = SYMBOLIC_LINK_FALLBACK_HEADER.length();

			for (int i = 0; i < len; i++) {
				if (br.read() != SYMBOLIC_LINK_FALLBACK_HEADER.charAt(i)) {
					return false;
				}
			}
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}

		return true;
	}
	
	public static boolean isSynthetic(final Path path) {
		if (path == null) {
			return false;
		}

		try (ObjectInputStream is = new ObjectInputStream(Files.newInputStream(path))) {
			readSyntheticHeader(is);
			return true;
		} catch (final StreamCorruptedException | EOFException e) {
	      // ObjectOutputStream이 아닌 경우
			return false;
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private static void readSyntheticHeader(final ObjectInputStream is) throws IOException, ClassNotFoundException {
		final Object header = is.readObject(); // NOSONAR findsecbugs:OBJECT_DESERIALIZATION 외부에서 전달된 파일이 아닌 내부적인 목적으로 생성된 파일이므로 문제 없음.

		if (!HEADER_SYNTHETIC.equals(header)) {
			throw new StreamCorruptedException("invalid synthetic header: " + header);
		}
	}

	private static void writeSyntheticHeader(final ObjectOutputStream os) throws IOException {
		    // TODO 편의상 일반 문자열로 헤더 처리. 추후 기능 확장 시 별도의 Header 객체를 writeObject.
		os.writeObject(HEADER_SYNTHETIC);
	}
}
