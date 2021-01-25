package jminusminus;

import java.util.ArrayList;
/**
 * The AST node for a try statement.
 */
public class JTryStatement extends JStatement{

    private JBlock tryblock;
    private ArrayList<JFormalParameter> catchparameters;
    private ArrayList<JBlock> catchblocks;
    private JBlock finallyblock;



    /**
     * Constructs an AST node for a while-statement.
     *
     * @param line      line in which the while-statement occurs in the source file.
     * @param tryblock the try block.
     * @param catchparameters      the list of catch parameters.
     * @param catchblocks the list of catch blocks.
     * @param finallyblock      the finally block.
     */
    public JTryStatement(int line, JBlock tryblock, ArrayList<JFormalParameter> catchparameters, ArrayList<JBlock> catchblocks,
                              JBlock finallyblock) {
        super(line);
        this.tryblock = tryblock;
        this.catchparameters = catchparameters;
        this.catchblocks = catchblocks;
        this.finallyblock = finallyblock;
    }
    /**
     * {@inheritDoc}
     */
    public JTryStatement analyze(Context context) {
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
        json.addChild("JTryStatement:" + line, e);
        JSONElement e1 = new JSONElement();
        e.addChild("tryblock", e1);
        tryblock.toJSON(e1);

        for(int i = 0; i < catchparameters.size(); i++) {
            JSONElement e2 = new JSONElement();
            e.addChild("catchparameters", e2);
            catchparameters.get(i).toJSON(e2);

            JSONElement e3 = new JSONElement();
            e.addChild("catchblocks", e3);
            catchblocks.get(i).toJSON(e3);

        }

        if (finallyblock != null) {
            JSONElement e4 = new JSONElement();
            e.addChild("finallyblock", e4);
            finallyblock.toJSON(e4);
        }
    }


}
