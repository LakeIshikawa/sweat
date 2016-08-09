package com.lksoft.yugen.stateless;

import com.badlogic.gdx.files.FileHandle;
import com.lksoft.yugen.FsmLexer;
import com.lksoft.yugen.FsmParser;
import com.lksoft.yugen.fsm.visitor.FighterExpVisitor;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

/**
 * Created by Stallman on 09/08/2016.
 */
public class HitPackReader {

    // File
    private FileHandle hitFile;

    /**
     * Creates a reader for a hit pack
     * @param hitFile
     */
    public HitPackReader(FileHandle hitFile){
        this.hitFile = hitFile;
    }

    /**
     * Read the hit pack
     * @return A hit pack
     */
    public HitPack read(){
        HitPack result = new HitPack();

        // Exp visitor for constant exp
        FighterExpVisitor expVisitor = new FighterExpVisitor(null);

        // Parse the hit file
        String text = hitFile.readString();
        String[] lines = text.split("\\n");

        HitPack.HitDef current = null;
        for( String line : lines ) {
            line = line.trim();

            // Hit def
            if (line.startsWith("[Hit ")) {
                // Commit last hit
                if( current != null ){
                    result.set(current.getName(), current);
                }

                // Create new animation
                String name = line.substring(6, line.indexOf(']'));
                current = new HitPack.HitDef(name);
            }

            // Var def
            else if( !line.isEmpty() ){
                // Create parser
                FsmLexer lexer = new FsmLexer(new ANTLRInputStream(line));
                FsmParser parser = new FsmParser(new CommonTokenStream(lexer));
                FsmParser.AssignmentContext ass = parser.assignment();

                ass.e().accept(expVisitor);
                current.set(ass.ID().getText(), expVisitor.getResult());
            }
        }

        return result;
    }
}
