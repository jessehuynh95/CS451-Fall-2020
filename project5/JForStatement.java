package jminusminus;
import java.util.ArrayList;

import static jminusminus.CLConstants.GOTO;

/**
 * The AST node for a for statement.
 */
public class JForStatement extends JStatement {

    private JVariableDeclaration initial;
    private JExpression fors;
    private JStatement updates;
    private JStatement statements;

    //ADDING LOCALCONTEXT FOR ANALYZE
    private LocalContext context;
    /**
     * Constructs an AST node for a for statement.
     *
     * @param line      line in which the for statement occurs in the source file.
     * @param statements   The body/statements of the for statement.
     *
     */

    public JForStatement(int line, JVariableDeclaration initial,
                         JExpression fors, JStatement updates, JStatement statements) {
        super(line);
        this.initial = initial;
        this.fors = fors;
        this.updates = updates;
        this.statements = statements;
    }
    /**
     * {@inheritDoc}
     */
    public JForStatement analyze(Context context) {
        //followed the instructions from the checklist
        //create a new local context
        LocalContext localcontext = new LocalContext(context);
        //analyze the init
        initial.analyze(localcontext);
        //analyze the condition
        fors.analyze(localcontext);
        //make sure the condition is a BOOLEAN
        fors.type().mustMatchExpected(line(), Type.BOOLEAN);
        //analyze the update
        updates = (JStatement) updates.analyze(localcontext);
        //analyze the body
        statements = (JStatement) statements.analyze(localcontext);
        return this;

    }




    /**
     * {@inheritDoc}
     */
    public void codegen(CLEmitter output) {
        //based of of JConditionalExpression, copy and pasted and modified from there
        initial.codegen(output);
        String forsIn = output.createLabel();
        String forsOut = output.createLabel();
        output.addLabel(forsIn);
        fors.codegen(output, forsOut, false);
        statements.codegen(output);
        updates.codegen(output);
        output.addBranchInstruction(GOTO, forsIn);
        output.addLabel(forsOut);
    }
    /**
     * {@inheritDoc}
     */
    public void toJSON(JSONElement json) {
        JSONElement e = new JSONElement();
        json.addChild("JForStatement:" + line, e);
        JSONElement e1 = new JSONElement();
        e.addChild("Init", e1);
        initial.toJSON(e1);
        JSONElement e2 = new JSONElement();
        e.addChild("Condition", e2);
        fors.toJSON(e2);
        JSONElement e3 = new JSONElement();
        e.addChild("Update", e3);
        updates.toJSON(e3);
        JSONElement e4 = new JSONElement();
        e.addChild("Body", e4);
        statements.toJSON(e4);

    }



}
