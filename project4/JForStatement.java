package jminusminus;
import java.util.ArrayList;

/**
 * The AST node for a for statement.
 */
public class JForStatement extends JStatement {

    private boolean counting;
    private JVariableDeclaration initial;
    private JExpression fors;
    private JStatement updates;
    private JStatement statements;
    private JFormalParameter param;


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
