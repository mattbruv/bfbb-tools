import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

class AsmFile {

    public Path path;
    public String content;

    AsmFile(Path path) throws IOException {
        this.path = path;
        var context = Files.readString(this.path);
        System.out.print(this.path);
        System.out.println(" " + context.lines().count());
        // System.out.println(context);
    }

}

public class AsmFiles {

    public List<AsmFile> files = new ArrayList<AsmFile>();

    AsmFiles() throws IOException {

        var path = Path.of("../bfbb/asm/");

        var files = Files.find(path, 999, (p, bfa) -> {
            return bfa.isRegularFile() && p.getFileName().toString().contains(".s");
        });

        for (var f : files.toArray(Path[]::new)) {
            this.files.add(new AsmFile(f));
        }
    }
}
