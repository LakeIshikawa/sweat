package com.lksoft.yugen.stateless;

import com.badlogic.gdx.files.FileHandle;
import com.lksoft.yugen.FsmLexer;
import com.lksoft.yugen.FsmParser;
import com.lksoft.yugen.fsmlang.visitor.JavaTranslatorVisitor;
import com.lksoft.yugen.fsmlang.visitor.SymbolTableVisitor;
import com.lksoft.yugen.stateful.Fsm;
import net.openhft.compiler.CompilerUtils;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;

/**
 * Created by Stallman on 17/08/2016.
 */
public class FsmReader {
    // .fsm file
    private FileHandle fsmFile;

    public FsmReader(FileHandle fsmFile) {
        this.fsmFile = fsmFile;
    }

    /**
     * Parse .fsm and create FSM class!
     * @return
     */
    public Class read() throws IOException {
        // Parse script
        FsmLexer lexer = new FsmLexer(new ANTLRInputStream(fsmFile.read()));
        FsmParser parser = new FsmParser(new CommonTokenStream(lexer));
        FsmParser.FsmContext fsm = parser.fsm();

        // TODO preprocess

        // First pass: symbols
        SymbolTableVisitor symbolTableVisitor = new SymbolTableVisitor();
        fsm.accept(symbolTableVisitor);

        // Second pass: Translation
        JavaTranslatorVisitor translator = new JavaTranslatorVisitor(symbolTableVisitor.getSymbolTable(), fsmFile.nameWithoutExtension());
        String javaCode = fsm.accept(translator);
        System.out.println(javaCode);

        String className = "com.lksoft.yugen.fsmlang." + fsmFile.nameWithoutExtension();
        Class javaClass = null;
        try {
            javaClass = CompilerUtils.CACHED_COMPILER.loadFromJava(className, javaCode);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return javaClass;
    }
}
