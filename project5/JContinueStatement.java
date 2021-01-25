package jminusminus;

public class JContinueStatement extends JStatement {

    /**
     * Construct an AST node for a continue statement.
     *
     * @param line
     *            line in which the empty statement occurs in the source file.
     */

    protected JContinueStatement(int line) {
        super(line);
    }

    /**
     * @inheritDoc
     */

    public JStatement analyze(Context context) {
        return this;
    }

    /**
     * @inheritDoc
     */

    public void codegen(CLEmitter output) {
    }

    /**
     * @inheritDoc
     */

    public void toJSON(JSONElement json) {
        JSONElement e = new JSONElement();
        json.addChild("JContinueStatement:" + line, e);
    }

}