package com.anonym.astran.helpers;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.nio.file.*;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileHelper {
    public static void extractZipStreamTo(InputStream zipInputStream, Path dest) throws IOException {
        try (ZipInputStream zis = new ZipInputStream(new BufferedInputStream(zipInputStream))) {
            ZipEntry e;
            while ((e = zis.getNextEntry()) != null) {
                if (e.isDirectory()) { zis.closeEntry(); continue; }
                Path out = dest.resolve(e.getName());
                Files.createDirectories(out.getParent());
                try (OutputStream os = Files.newOutputStream(out, StandardOpenOption.CREATE_NEW)) {
                    zis.transferTo(os);
                } catch (FileAlreadyExistsException fae) {
                    try (OutputStream os = Files.newOutputStream(out, StandardOpenOption.TRUNCATE_EXISTING)) {
                    }
                }
                zis.closeEntry();
            }
        }
    }

    public static void copyDirectory(Path src, Path dst) throws IOException {
        try (var stream = Files.walk(src)) {
            stream.forEach(source -> {
                try {
                    Path relative = src.relativize(source);
                    Path target = dst.resolve(relative.toString());
                    if (Files.isDirectory(source)) {
                        Files.createDirectories(target);
                    } else {
                        Files.createDirectories(target.getParent());
                        Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
                    }
                } catch (IOException ex) {
                    throw new UncheckedIOException(ex);
                }
            });
        } catch (UncheckedIOException uio) {
            throw uio.getCause();
        }
    }

    public static void extractFromJar(Class<?> anchorClass, String prefix, Path dest) throws IOException {
        if (!prefix.endsWith("/")) prefix = prefix + "/";
        URL url = anchorClass.getResource("/" + prefix);
        if (url == null) throw new IOException("Resource prefix not found in jar: " + prefix);

        Path codeSourcePath;
        try {
            codeSourcePath = Paths.get(anchorClass.getProtectionDomain().getCodeSource().getLocation().toURI());
        } catch (Exception e) {
            throw new IOException("Failed to locate code source for anchor class", e);
        }

        if (!Files.isRegularFile(codeSourcePath)) {
            throw new IOException("Expected mod to be packaged as jar but code source is not a file: " + codeSourcePath);
        }

        try (JarFile jar = new JarFile(codeSourcePath.toFile())) {
            Enumeration<JarEntry> entries = jar.entries();
            while (entries.hasMoreElements()) {
                JarEntry je = entries.nextElement();
                String name = je.getName();
                if (!name.startsWith(prefix)) continue;
                if (je.isDirectory()) continue;
                String rel = name.substring(prefix.length());
                Path out = dest.resolve(rel);
                Files.createDirectories(out.getParent());
                try (InputStream is = jar.getInputStream(je)) {
                    Files.copy(is, out, StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }
    }

    public static void extractFromJarUsingCodeSource(Class<?> anchorClass, String prefix, Path dest) throws IOException {
        URI codeSourceUri;
        try {
            codeSourceUri = anchorClass.getProtectionDomain().getCodeSource().getLocation().toURI();
        } catch (Exception e) {
            throw new IOException(e);
        }
        Path codeSourcePath = Paths.get(codeSourceUri);
        if (Files.isDirectory(codeSourcePath)) {
            Path assetsRoot = codeSourcePath.resolve("resources/main").resolve("assets");
            Path src = assetsRoot.resolve(prefix);
            if (Files.exists(src)) {
                copyDirectory(src, dest);
                return;
            } else {
                URL folderUrl = anchorClass.getResource("/" + prefix);
                if (folderUrl != null && "file".equals(folderUrl.getProtocol())) {
                    try {
                        Path src2 = Paths.get(folderUrl.toURI());
                        copyDirectory(src2, dest);
                        return;
                    } catch (Exception ignored) { }
                }
            }
            throw new IOException("Could not locate assets in dev layout for prefix: " + prefix);
        } else {
            try (JarFile jar = new JarFile(codeSourcePath.toFile())) {
                Enumeration<JarEntry> entries = jar.entries();
                if (!prefix.endsWith("/")) prefix = prefix + "/";
                while (entries.hasMoreElements()) {
                    JarEntry je = entries.nextElement();
                    String name = je.getName();
                    if (!name.startsWith(prefix)) continue;
                    if (je.isDirectory()) continue;
                    String rel = name.substring(prefix.length());
                    Path out = dest.resolve(rel);
                    Files.createDirectories(out.getParent());
                    try (InputStream is = jar.getInputStream(je)) {
                        Files.copy(is, out, StandardCopyOption.REPLACE_EXISTING);
                    }
                }
            }
        }
    }

    public static void deleteRecursively(Path path) throws IOException {
        if (!Files.exists(path)) return;
        try (var stream = Files.walk(path)) {
            stream.sorted(Comparator.reverseOrder()).forEach(p -> {
                try { Files.deleteIfExists(p); } catch (IOException ignored) {}
            });
        }
    }
}

