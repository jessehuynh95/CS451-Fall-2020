package jminusminus;

/**
 * The AST node for a throw statement.
 */
public class JThrowStatement extends JStatement {

    private JExpression expression;



    /**
     * Constructs an AST node for a while-statement.
     *
     * @param line      line in which the throw statement occurs in the source file.
     * @param expression expression thrown
     *                  .
     */
    public JThrowStatement(int line, JExpression expression) {
        super(line);
        this.expression = expression;
    }

    /**
     * {@inheritDoc}
     */
    public JStatement analyze(Context context) {
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
        json.addChild("JThrowStatement:" + line, e);
        JSONElement e1 = new JSONElement();
        e.addChild("expression", e1);
        expression.toJSON(e1);
    }
}