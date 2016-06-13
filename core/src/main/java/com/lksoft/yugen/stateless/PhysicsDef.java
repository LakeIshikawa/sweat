package com.lksoft.yugen.stateless;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.lksoft.yugen.FsmLexer;
import com.lksoft.yugen.FsmParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;

/**
 * Created by Lake on 12/06/2016.
 */
public class PhysicsDef {
    // Stateless triggers
    private Array<FighterState.FighterTrigger> triggers = new Array<>();

    /**
     * Creates a physics def from a phy file
     * @param phyFile The .phy file
     */
    public PhysicsDef(FileHandle phyFile) throws IOException {
        // Parse script
        FsmLexer lexer = new FsmLexer(new ANTLRInputStream(phyFile.read()));
        FsmParser parser = new FsmParser(new CommonTokenStream(lexer));
        FsmParser.TriggersContext fsm = parser.triggers();

        // Create params and states
        PhysicsSetupVisitor visitor = new PhysicsSetupVisitor(this);
        fsm.accept(visitor);
    }

    public Array<FighterState.FighterTrigger> getTriggers() {
        return triggers;
    }
}
