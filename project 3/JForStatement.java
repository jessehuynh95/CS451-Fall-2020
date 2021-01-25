package jminusminus;
import java.util.ArrayList;

/**
 * The AST node for a for statement.
 */
public class JForStatement extends JStatement {

    private boolean counting;
    private JVariableDeclaration initial;
    private JExpression terminate;
    private JStatement increment;
    private JStatement statements;

    /**
     * Constructs an AST node for a for statement.
     *
     * @param line      line in which the for statement occurs in the source file.
     * @param initial the initialization experession for the for statement.
     * @param terminate    termination expression for the for statement.
     * @param increment incremental expression for the for statement.
     * @param statements   The body/statements of the for statement.
     *
     */

    public JForStatement(int line, JVariableDeclaration initial, JExpression terminate,
                         JStatement increment, JStatement statements) {
        super(line);
        this.counting = increment == null ? true : false;
        this.initial = initial;
        this.terminate = terminate;
        this.increment = increment;
        this.statements = statements;
    }


    /**
     * {@inheritDoc}
     */
    public JForStatement analyze(Context context) {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public void codegen(CLEmitter output) {

    }

    /**
     * {@inheritDoc}
     */
    public void toJSON(JSONElement json) {
        JSONElement e = new JSONElement();
        json.addChild("JForStatement:" + line, e);
        JSONElement e1 = new JSONElement();
        if(counting) {
            e.addChild("terminate", e1);
            terminate.toJSON(e1);
        } else {
            e.addChild("terminate", e1);
            terminate.toJSON(e1);
        }
        JSONElement e2 = new JSONElement();
        if(!counting) {
            e.addChild("increment", e2);
            increment.toJSON(e2);
        }
        JSONElement e3 = new JSONElement();
        if(statements != null) {
            statements.toJSON(e3);
        }

    }



}
