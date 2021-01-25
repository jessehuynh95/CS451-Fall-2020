package jminusminus;

import java.util.ArrayList;

class JSwitchStatement extends JStatement {

    private JExpression parExpression;
    private ArrayList<SwitchStatementGroup> switchBlockStatementGroup;

    public JSwitchStatement(int line, JExpression parExp, ArrayList<SwitchStatementGroup> switchBlockStatementGroup) {
        super(line);
        this.parExpression = parExp;
        this.switchBlockStatementGroup = switchBlockStatementGroup;
    }

    public JSwitchStatement analyze(Context context) {
        return this;
    }

    public void codegen(CLEmitter output) {
    }

    public void toJSON(JSONElement json) {
        JSONElement e = new JSONElement();
        json.addChild("JSwitchStatement:" + line, e);
        JSONElement e1 = new JSONElement();
        e.addChild("parExpression", e1);
        parExpression.toJSON(e1);
        JSONElement e2 = new JSONElement();
        for(SwitchStatementGroup sta : switchBlockStatementGroup) {
            e.addChild("sta", e2);
            sta.toJSON(e2);
        }
    }



}

 /**

    private JExpression condition;

    private ArrayList<JBlock> switchBlocks;
    private ArrayList<String> cases;

    public JSwitchStatement(int line, JExpression condition, JSwitchBlock body) {
        super(line);
        this.condition = condition;
        this.cases = body.cases;
        this.switchBlocks = body.switchBlocks;
    }

    public JStatement analyze(Context context) {
        return this;
    }

    public void codegen(CLEmitter output) {
    }



    public void toJSON(JSONElement json) {
        JSONElement e = new JSONElement();
        json.addChild("JSwitchStatement:" + line, e);
        JSONElement e1 = new JSONElement();
        e.addChild("condition", e1);
        condition.toJSON(e1);
        JSONElement e2 = new JSONElement();
        for(int i = 0; i < cases.size(); i++) {
            e.addChild("switchBlocks", e2);
            switchBlocks.get(i).toJSON(e2);
        }
    }
*/




class SwitchStatementGroup extends JStatement {

    private ArrayList<JExpression> switchLabels;
    private JStatement blockStatement;

    public SwitchStatementGroup(int line, ArrayList<JExpression> switchLabels,
                                 JStatement blockStatement) {
        super(line);
        this.switchLabels = switchLabels;
        this.blockStatement = blockStatement;
    }

    public JAST analyze(Context context) {
        return this;
    }

    public void codegen(CLEmitter output) {

    }

    public void toJSON(JSONElement json) {
        JSONElement e = new JSONElement();
        json.addChild("SwitchStatementGroup:" + line, e);
        for (JExpression exp : switchLabels) {
            if (exp != null) {
                JSONElement e1 = new JSONElement();
                e.addChild("sta", e1);
                exp.toJSON(e1);
            } else {
                break;
            }
        }
        JSONElement e2 = new JSONElement();
        e.addChild("blockStatement", e2);
        blockStatement.toJSON(e2);

        }
    }



class JSwitchBlock extends JStatement {

    /** List of statements forming the case(s). */
    protected ArrayList<String> cases;
    protected ArrayList<JBlock> switchBlocks;


    public JSwitchBlock(int line, ArrayList<String> cases,  ArrayList<JBlock> switchBlocks) {
        super(line);
        this.cases = cases;
        this.switchBlocks = switchBlocks;
    }



    public JSwitchBlock analyze(Context context) {

        return this;
    }


    public void codegen(CLEmitter output) {
    }

    /**
     * @inheritDoc
     */


    public void toJSON(JSONElement json) {
        JSONElement e = new JSONElement();
        json.addChild("JSwitchBlock:" + line, e);


    }
}