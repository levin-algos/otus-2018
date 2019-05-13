package ru.otus.algo;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Function;

public class FileConverter {

    private final Path source;
    private final Path dest;

    private static final int BUFFER_SIZE = 1024;

    public FileConverter(Path source, Path dest) {
        Objects.requireNonNull(source);
        Objects.requireNonNull(dest);

        this.source = source;
        this.dest = dest;
    }

    public void convert(Function<ByteBuffer, ByteBuffer> action) {
        try (RandomAccessFile inFile = new RandomAccessFile(source.toString(), "r");
             RandomAccessFile outFile = new RandomAccessFile(dest.toString(), "rw")) {

            FileChannel in = inFile.getChannel();
            FileChannel out = outFile.getChannel();
            ByteBuffer buf;
            do {
                buf = read(in);
                if (buf!= null) {
                    write(out, action.apply(buf));
                }
            } while(buf != null);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ByteBuffer read(FileChannel in) throws IOException {
        long size = in.size();
        long delta = size - in.position();
        if (delta < 1)
            return null;
        ByteBuffer buffer = ByteBuffer.allocate(delta > BUFFER_SIZE ? BUFFER_SIZE : (int) delta);
        in.read(buffer);
        return buffer;
    }

    private void write(FileChannel out, ByteBuffer buffer) throws IOException {
        out.write(buffer);
    }

    static boolean contentEquals(Path a, Path b) {
        try (RandomAccessFile file1 = new RandomAccessFile(a.toString(), "r");
            RandomAccessFile file2 = new RandomAccessFile(b.toString(), "r")) {

            final FileChannel ch1 = file1.getChannel();
            final FileChannel ch2 = file2.getChannel();
            if (ch1.size() != ch2.size())
                return false;

            int c1, c2;
            do {
                ByteBuffer buffer1 = ByteBuffer.allocate(BUFFER_SIZE);
                ByteBuffer buffer2 = ByteBuffer.allocate(BUFFER_SIZE);
                c1 = ch1.read(buffer1);
                c2 = ch2.read(buffer2);
                if (c1 != c2)
                    return false;

                if (!buffer1.equals(buffer2))
                    return false;
            } while (c1 != -1);

            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
