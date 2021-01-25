package jminusminus;

import static jminusminus.CLConstants.GOTO;

/**
 * The AST node for a  do statement.
 */
class JDoStatement extends JStatement {

    // test expression
    private JExpression whilethis;
    // the body
    private JStatement dothis;


    /**
     * Constructs an AST node for a do statement.
     *
     * @param line      line in which the do statement occurs in the source file.
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
    public JDoStatement analyze(Context context) {
        //modifying analyze for JIfStatement
        whilethis = (JExpression) whilethis.analyze(context);
        whilethis.type().mustMatchExpected(line(), Type.BOOLEAN);
        dothis = (JStatement) dothis.analyze(context);
        return this;
        
    }

    /**
     * {@inheritDoc}
     */
    public void codegen(CLEmitter output) {
        //base taken and modified from JIfStatement
        String elseLabel = output.createLabel();
        String endLabel = output.createLabel();
        String startLabel = output.createLabel();
        output.addLabel(endLabel);
        dothis.codegen(output);
        output.addLabel(startLabel);
        whilethis.codegen(output, elseLabel, false);
        output.addBranchInstruction(GOTO, endLabel);
        output.addLabel(elseLabel);

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

