package jminusminus;

import static jminusminus.CLConstants.*;

/**
 * The AST node for a Double literal.
 */
class JLiteralDouble extends JExpression {
    // String representation of the literal.
    private String text;

    /**
     * Constructs an AST node for a double literal given its line number and string representation.
     *
     * @param line line in which the literal occurs in the source file.
     * @param text string representation of the literal.
     */
    public JLiteralDouble(int line, String text) {
        super(line);
        this.text = text;
    }

    public JExpression analyze(Context context) {
        //Straightforward, completed this in a previous homework
        type = Type.DOUBLE;
        return this;
    }


    /**
     * {@inheritDoc}
     */
    public void codegen(CLEmitter output) {
        double d = Double.parseDouble(text);
        if(d == 0D)
        {output.addNoArgInstruction(DCONST_0);}
        else if (d == 1D)
        {output.addNoArgInstruction(DCONST_1);}
        else {output.addLDCInstruction(d);}
    }


    public void toJSON(JSONElement json) {
        JSONElement e = new JSONElement();
        json.addChild("JLiteralDouble:" + line, e);
        e.addAttribute("type", type == null ? "" : type.toString());
        e.addAttribute("value", text);
    }
}
