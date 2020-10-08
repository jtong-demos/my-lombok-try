package dev.jtong.demos.my.lombok;

import com.sun.tools.javac.main.JavaCompiler;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.util.Context;

import dev.jtong.demos.my.lombok.setter.MySetterProcessor;
import org.junit.jupiter.api.Test;

import javax.annotation.processing.AbstractProcessor;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class MySetterProcessorTest {
    @Test
    public void should_compile() throws URISyntaxException {
        JavaCompiler compiler = new JavaCompiler(new Context());
        Set<AbstractProcessor> processors = Collections.singleton(new MySetterProcessor());
        compiler.initProcessAnnotations(processors);

        List<JCTree.JCCompilationUnit> roots = new ArrayList<JCTree.JCCompilationUnit>();
        List<File> filesToParse = new ArrayList<File>();
        //可以去掉import以确认确实是变异了这个文件
        URL resource = getClass().getClassLoader().getResource("OMySetter.java");
        Path path = Paths.get(resource.toURI());
        path.toFile();
        filesToParse.add(path.toFile());

        for (File fileToParse : filesToParse) {
            JCTree.JCCompilationUnit unit = compiler.parse(fileToParse.getAbsolutePath());
//            baseMap.put(unit, fileToBase.get(fileToParse));
            roots.add(unit);
        }

        com.sun.tools.javac.util.List<JCTree.JCCompilationUnit> trees = compiler.enterTrees(toJavacList(roots));


        compiler.processAnnotations(trees);
    }

    private static <T> com.sun.tools.javac.util.List<T> toJavacList(List<T> list) {
        com.sun.tools.javac.util.List<T> out = com.sun.tools.javac.util.List.nil();
        ListIterator<T> li = list.listIterator(list.size());
        while (li.hasPrevious()) out = out.prepend(li.previous());
        return out;
    }

}
