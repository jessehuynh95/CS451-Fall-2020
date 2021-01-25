package jminusminus;
/**
 * The AST node for a  do statement.
 */
class JDoStatement extends JStatement {

    // test expression
    private JExpression whilethis;
    // the body
    private JStatement dothis;


    /**
     * Constructs an AST node for a while-statement.
     *
     * @param line      line in which the while-statement occurs in the source file.
     * @param whilethis test expression.
     * @param dothis    the body.
     */
    public JDoStatement(int line, JStatement dothis, JExpression whilethis) {
        super(line);
        this.whilethis = whilethis;
        this.dothis = dothis;
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

    public void toJSON(JSONElement json) {
        JSONElement e = new JSONElement();
        json.addChild("JDoStatement:" + line, e);
        JSONElement e1 = new JSONElement();
        e.addChild("dothis", e1);
        dothis.toJSON(e1);
        JSONElement e2 = new JSONElement();
        e.addChild("whilethis", e2);
        whilethis.toJSON(e2);
    }

}

