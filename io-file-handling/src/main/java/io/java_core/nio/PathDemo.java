package io.java_core.nio;

import java.nio.file.Path;

public class PathDemo {
    public static void pathUsage() {


//      Path.of()
        Path p = Path.of("io-file-handling","src");
        System.out.println("Path = " + p);



//      Path.toAbsolutePath() - Absolute path till path p
        Path pAbs = p.toAbsolutePath();
        System.out.println("Absolute path = " + pAbs);



//      Path.resolve() - Joins / Appends paths
        Path pResolve = p.resolve("application.txt");
        System.out.println("Resolved path for text file = " + pResolve);


//      Path.relative() - Computes the relative path b/w two absolute path or two relative path
        Path pRelative = Path.of("io-file-handling","src","main","java","io")
                .relativize(Path.of("io-file-handling","src","main","resources","application.txt"));
        System.out.println("Relative path = " + pRelative);




//      user directory
        System.out.println("Current User Dir = " + System.getProperty("user.dir"));
    }
}
