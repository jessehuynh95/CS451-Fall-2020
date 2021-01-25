package jminusminus;


import static jminusminus.CLConstants.GOTO;

/**
 * The AST node for a  conditional statement.
 */
class JConditionalExpression extends JExpression {

    //test condition
    private JExpression condition;
    //first expression
    private JExpression ThenPart;
    //second expression
    private JExpression ElsePart;

    /**
     * {@inheritDoc}
     */
    public JConditionalExpression(int line, JExpression lhs, JExpression ThenPart,
                                  JExpression ElsePart) {
        super(line);
        this.condition = lhs;
        this.ThenPart = ThenPart;
        this.ElsePart = ElsePart;
    }



    /**
     * {@inheritDoc}
     */
    public JExpression analyze(Context context) {
        //modified from JIfStatement
        condition = (JExpression) condition.analyze(context);
        condition.type().mustMatchExpected(line(), Type.BOOLEAN);
        ThenPart = (JExpression) ThenPart.analyze(context);
        ElsePart = (JExpression) ElsePart.analyze(context);
        ElsePart.type().mustMatchExpected(line(), ThenPart.type());
        type = ThenPart.type();
        return this;

    }

    /**
     * {@inheritDoc}
     */
    public void codegen(CLEmitter output) {
        //modified from JIfStatement
        String elseLabel = output.createLabel();
        String endLabel = output.createLabel();
        condition.codegen(output, elseLabel, false);
        ThenPart.codegen(output);
        output.addBranchInstruction(GOTO, endLabel);
        output.addLabel(elseLabel);
        ElsePart.codegen(output);
        output.addLabel(endLabel);
        }


    /**
     * {@inheritDoc}
     */
    public void toJSON (JSONElement json) {
        JSONElement e = new JSONElement();
        json.addChild("JConditionalExpression:" + line, e);
        JSONElement e1 = new JSONElement();
        e.addChild("Condition", e1);
        condition.toJSON(e1);
        JSONElement e2 = new JSONElement();
        e.addChild("ThenPart", e2);
        ThenPart.toJSON(e2);
        JSONElement e3 = new JSONElement();
        e.addChild("ElsePart", e3);
        ElsePart.toJSON(e3);
    }


}