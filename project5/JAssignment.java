// Copyright 2012- Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package jminusminus;

import static jminusminus.CLConstants.*;

/**
 * This abstract base class is the AST node for an assignment operation.
 */
abstract class JAssignment extends JBinaryExpression {
    /**
     * Constructs an AST node for an assignment operation.
     *
     * @param line     line in which the assignment operation occurs in the source file.
     * @param operator the assignment operator.
     * @param lhs      the lhs operand.
     * @param rhs      the rhs operand.
     */
    public JAssignment(int line, String operator, JExpression lhs, JExpression rhs) {
        super(line, operator, lhs, rhs);
    }
}

/**
 * The AST node for a left shift-assign (<<=) operation.
 */
class JALeftShiftAssignOp extends JAssignment {
    /**
     * Constructs the AST node for a left shift-assign (<<=) operation.
     *
     * @param line line in which the assignment operation occurs in the source file.
     * @param lhs  the lhs operand.
     * @param rhs  the rhs operand.
     */
    public JALeftShiftAssignOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "<<=", lhs, rhs);
    }

    /**
     * {@inheritDoc}
     */
    public JALeftShiftAssignOp analyze(Context context) {
        //Taken from JMinusAssignOp and double removed
        if (!(lhs instanceof JLhs)) {
            JAST.compilationUnit.reportSemanticError(line(), "Illegal lhs for assignment");
        } else {
            lhs = (JExpression) ((JLhs) lhs).analyzeLhs(context);
        }
        rhs = (JExpression) rhs.analyze(context);

        if (lhs.type().equals(Type.INT)) {
            rhs.type().mustMatchExpected(line(), Type.INT);
            type = Type.INT;
        } else if (lhs.type().equals(Type.LONG)) {
            rhs.type().mustMatchExpected(line(), Type.LONG);
            type = Type.LONG;
        } else if (lhs.type().equals(Type.STRING)) {
            rhs = (new JStringConcatenationOp(line, lhs, rhs)).analyze(context);
            type = Type.STRING;
        } else {
            JAST.compilationUnit.reportSemanticError(line(),
                    "Invalid lhs type for +=: " + lhs.type());
        }
        return this;

    }

    /**
     * {@inheritDoc}
     */
    public void codegen(CLEmitter output) {
        //modifying codegen from JMinusAssignOp
        ((JLhs) lhs).codegenLoadLhsLvalue(output);
        if (lhs.type().equals(Type.STRING)) {
            rhs.codegen(output);
        } else if (lhs.type().equals(Type.LONG)) {
            ((JLhs) lhs).codegenLoadLhsRvalue(output);
            rhs.codegen(output);
            output.addNoArgInstruction(LSHL);
        } else if (lhs.type().equals(Type.INT)){
            ((JLhs) lhs).codegenLoadLhsRvalue(output);
            rhs.codegen(output);
            output.addNoArgInstruction(ISHL);
        }
        if (!isStatementExpression) {
            ((JLhs) lhs).codegenDuplicateRvalue(output);
        }
        ((JLhs) lhs).codegenStore(output);
    }
}

/**
 * The AST node for a and-assign (&=) operation.
 */
class JAndAssignOp extends JAssignment {
    /**
     * Constructs the AST node for a and-assign (&=) operation.
     *
     * @param line line in which the assignment operation occurs in the source file.
     * @param lhs  the lhs operand.
     * @param rhs  the rhs operand.
     */
    public JAndAssignOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "&=", lhs, rhs);
    }

    /**
     * {@inheritDoc}
     */
    public JAndAssignOp analyze(Context context) {
        //Taken from JALeftShiftAssignOp
        if (!(lhs instanceof JLhs)) {
            JAST.compilationUnit.reportSemanticError(line(), "Illegal lhs for assignment");
        } else {
            lhs = (JExpression) ((JLhs) lhs).analyzeLhs(context);
        }
        rhs = (JExpression) rhs.analyze(context);

        if (lhs.type().equals(Type.INT)) {
            rhs.type().mustMatchExpected(line(), Type.INT);
            type = Type.INT;
        } else if (lhs.type().equals(Type.LONG)) {
            rhs.type().mustMatchExpected(line(), Type.LONG);
            type = Type.LONG;
        } else if (lhs.type().equals(Type.STRING)) {
            rhs = (new JStringConcatenationOp(line, lhs, rhs)).analyze(context);
            type = Type.STRING;
        } else {
            JAST.compilationUnit.reportSemanticError(line(),
                    "Invalid lhs type for +=: " + lhs.type());
        }
        return this;

    }

    /**
     * {@inheritDoc}
     */
    public void codegen(CLEmitter output) {
        //modifying codegen from JALeftShiftAssignOp
        ((JLhs) lhs).codegenLoadLhsLvalue(output);
        if (lhs.type().equals(Type.STRING)) {
            rhs.codegen(output);
        } else if (lhs.type().equals(Type.LONG)) {
            ((JLhs) lhs).codegenLoadLhsRvalue(output);
            rhs.codegen(output);
            output.addNoArgInstruction(LAND);
        } else if (lhs.type().equals(Type.INT)){
            ((JLhs) lhs).codegenLoadLhsRvalue(output);
            rhs.codegen(output);
            output.addNoArgInstruction(IAND);
        }
        if (!isStatementExpression) {
            ((JLhs) lhs).codegenDuplicateRvalue(output);
        }
        ((JLhs) lhs).codegenStore(output);
    }
}

/**
 * The AST node for a right shift-assign (>>=) operation.
 */
class JARightShiftAssignOp extends JAssignment {
    /**
     * Constructs the AST node for a right shift-assign (>>=) operation.
     *
     * @param line line in which the assignment operation occurs in the source file.
     * @param lhs  the lhs operand.
     * @param rhs  the rhs operand.
     */
    public JARightShiftAssignOp(int line, JExpression lhs, JExpression rhs) {
        super(line, ">>=", lhs, rhs);
    }

    /**
     * {@inheritDoc}
     */
    public JARightShiftAssignOp analyze(Context context) {
        //Taken from JALefttShiftAssignOp
        if (!(lhs instanceof JLhs)) {
            JAST.compilationUnit.reportSemanticError(line(), "Illegal lhs for assignment");
        } else {
            lhs = (JExpression) ((JLhs) lhs).analyzeLhs(context);
        }
        rhs = (JExpression) rhs.analyze(context);

        if (lhs.type().equals(Type.INT)) {
            rhs.type().mustMatchExpected(line(), Type.INT);
            type = Type.INT;
        } else if (lhs.type().equals(Type.LONG)) {
            rhs.type().mustMatchExpected(line(), Type.LONG);
            type = Type.LONG;
        } else if (lhs.type().equals(Type.STRING)) {
            rhs = (new JStringConcatenationOp(line, lhs, rhs)).analyze(context);
            type = Type.STRING;
        } else {
            JAST.compilationUnit.reportSemanticError(line(),
                    "Invalid lhs type for +=: " + lhs.type());
        }
        return this;

    }

    /**
     * {@inheritDoc}
     */
    public void codegen(CLEmitter output) {
        //modifying codegen from JALefttShiftAssignOp
        ((JLhs) lhs).codegenLoadLhsLvalue(output);
        if (lhs.type().equals(Type.STRING)) {
            rhs.codegen(output);
        } else if (lhs.type().equals(Type.LONG)) {
            ((JLhs) lhs).codegenLoadLhsRvalue(output);
            rhs.codegen(output);
            output.addNoArgInstruction(LSHR);
        } else if (lhs.type().equals(Type.INT)){
            ((JLhs) lhs).codegenLoadLhsRvalue(output);
            rhs.codegen(output);
            output.addNoArgInstruction(ISHR);
        }
        if (!isStatementExpression) {
            ((JLhs) lhs).codegenDuplicateRvalue(output);
        }
        ((JLhs) lhs).codegenStore(output);
    }
}


/**
 * The AST node for an assignment (=) operation.
 */
class JAssignOp extends JAssignment {
    /**
     * Constructs the AST node for an assignment (=) operation..
     *
     * @param line line in which the assignment operation occurs in the source file.
     * @param lhs  lhs operand.
     * @param rhs  rhs operand.
     */
    public JAssignOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "=", lhs, rhs);
    }

    /**
     * {@inheritDoc}
     */
    public JExpression analyze(Context context) {
        if (!(lhs instanceof JLhs)) {
            JAST.compilationUnit.reportSemanticError(line(), "Illegal lhs for assignment");
        } else {
            lhs = (JExpression) ((JLhs) lhs).analyzeLhs(context);
        }
        rhs = (JExpression) rhs.analyze(context);
        rhs.type().mustMatchExpected(line(), lhs.type());
        type = rhs.type();
        if (lhs instanceof JVariable) {
            IDefn defn = ((JVariable) lhs).iDefn();
            if (defn != null) {
                // Local variable; consider it to be initialized now.
                ((LocalVariableDefn) defn).initialize();
            }
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public void codegen(CLEmitter output) {
        ((JLhs) lhs).codegenLoadLhsLvalue(output);
        rhs.codegen(output);
        if (!isStatementExpression) {
            ((JLhs) lhs).codegenDuplicateRvalue(output);
        }
        ((JLhs) lhs).codegenStore(output);
    }
}



/**
 * The AST node for a div-assign (/=) operation.
 */
class JDivAssignOp extends JAssignment {
    /**
     * Constructs the AST node for a div-assign (/=) operation.
     *
     * @param line line in which the assignment operation occurs in the source file.
     * @param lhs  the lhs operand.
     * @param rhs  the rhs operand.
     */
    public JDivAssignOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "/=", lhs, rhs);
    }

    /**
     * {@inheritDoc}
     */
    public JExpression analyze(Context context) {
        //Taken from JAssignOp
        if (!(lhs instanceof JLhs)) {
            JAST.compilationUnit.reportSemanticError(line(), "Illegal lhs for assignment");
        } else {
            lhs = (JExpression) ((JLhs) lhs).analyzeLhs(context);
        }
        rhs = (JExpression) rhs.analyze(context);

        //taken inspiration from JPlusAssignOp and modified
        if (lhs.type().equals(Type.INT)) {
            rhs.type().mustMatchExpected(line(), Type.INT);
            type = Type.INT;
        } else if (lhs.type().equals(Type.DOUBLE)) {
            rhs.type().mustMatchExpected(line(), Type.DOUBLE);
            type = Type.DOUBLE;
        } else if (lhs.type().equals(Type.LONG)) {
            rhs.type().mustMatchExpected(line(), Type.LONG);
            type = Type.LONG;
        } else if (lhs.type().equals(Type.STRING)) {
            rhs = (new JStringConcatenationOp(line, lhs, rhs)).analyze(context);
            type = Type.STRING;
        } else {
            JAST.compilationUnit.reportSemanticError(line(),
                    "Invalid lhs type for +=: " + lhs.type());
        }
        return this;

    }

    /**
     * {@inheritDoc}
     */
    public void codegen(CLEmitter output) {
        //modifying codegen from JPlusAssignOp
        ((JLhs) lhs).codegenLoadLhsLvalue(output);
        if (lhs.type().equals(Type.STRING)) {
            rhs.codegen(output);
        } else if (lhs.type().equals(Type.DOUBLE)) {
            ((JLhs) lhs).codegenLoadLhsRvalue(output);
            rhs.codegen(output);
            output.addNoArgInstruction(DDIV);
        } else if (lhs.type().equals(Type.LONG)) {
            ((JLhs) lhs).codegenLoadLhsRvalue(output);
            rhs.codegen(output);
            output.addNoArgInstruction(LDIV);
        } else if (lhs.type().equals(Type.INT)){
            ((JLhs) lhs).codegenLoadLhsRvalue(output);
            rhs.codegen(output);
            output.addNoArgInstruction(IDIV);
        }
        if (!isStatementExpression) {
            ((JLhs) lhs).codegenDuplicateRvalue(output);
        }
        ((JLhs) lhs).codegenStore(output);
    }
}

/**
 * The AST node for an unsigned right shift-assign (>>>=) operation.
 */
class JLRightShiftAssignOp extends JAssignment {
    /**
     * Constructs the AST node for a right shift-assign (>>>=) operation.
     *
     * @param line line in which the assignment operation occurs in the source file.
     * @param lhs  the lhs operand.
     * @param rhs  the rhs operand.
     */
    public JLRightShiftAssignOp(int line, JExpression lhs, JExpression rhs) {
        super(line, ">>>=", lhs, rhs);
    }

    /**
     * {@inheritDoc}
     */
    public JExpression analyze(Context context) {
        //Taken from JALefttShiftAssignOp
        if (!(lhs instanceof JLhs)) {
            JAST.compilationUnit.reportSemanticError(line(), "Illegal lhs for assignment");
        } else {
            lhs = (JExpression) ((JLhs) lhs).analyzeLhs(context);
        }
        rhs = (JExpression) rhs.analyze(context);

        if (lhs.type().equals(Type.INT)) {
            rhs.type().mustMatchExpected(line(), Type.INT);
            type = Type.INT;
        } else if (lhs.type().equals(Type.LONG)) {
            rhs.type().mustMatchExpected(line(), Type.LONG);
            type = Type.LONG;
        } else if (lhs.type().equals(Type.STRING)) {
            rhs = (new JStringConcatenationOp(line, lhs, rhs)).analyze(context);
            type = Type.STRING;
        } else {
            JAST.compilationUnit.reportSemanticError(line(),
                    "Invalid lhs type for +=: " + lhs.type());
        }
        return this;

    }

    /**
     * {@inheritDoc}
     */
    public void codegen(CLEmitter output) {
        //modifying codegen from JARighttShiftAssignOp
        ((JLhs) lhs).codegenLoadLhsLvalue(output);
        if (lhs.type().equals(Type.STRING)) {
            rhs.codegen(output);
        } else if (lhs.type().equals(Type.LONG)) {
            ((JLhs) lhs).codegenLoadLhsRvalue(output);
            rhs.codegen(output);
            output.addNoArgInstruction(LUSHR);
        } else if (lhs.type().equals(Type.INT)){
            ((JLhs) lhs).codegenLoadLhsRvalue(output);
            rhs.codegen(output);
            output.addNoArgInstruction(IUSHR);
        }
        if (!isStatementExpression) {
            ((JLhs) lhs).codegenDuplicateRvalue(output);
        }
        ((JLhs) lhs).codegenStore(output);
    }
}


/**
 * The AST node for a minus-assign (-=) operation.
 */
class JMinusAssignOp extends JAssignment {
    /**
     * Constructs the AST node for a minus-assign (-=) operation.
     *
     * @param line line in which the assignment operation occurs in the source file.
     * @param lhs  the lhs operand.
     * @param rhs  the rhs operand.
     */
    public JMinusAssignOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "-=", lhs, rhs);
    }

    /**
     * {@inheritDoc}
     */
    public JExpression analyze(Context context) {
        //Taken from JAssignOp
        if (!(lhs instanceof JLhs)) {
            JAST.compilationUnit.reportSemanticError(line(), "Illegal lhs for assignment");
        } else {
            lhs = (JExpression) ((JLhs) lhs).analyzeLhs(context);
        }
        rhs = (JExpression) rhs.analyze(context);

        //taken inspiration from JPlusAssignOp and modified
        if (lhs.type().equals(Type.INT)) {
            rhs.type().mustMatchExpected(line(), Type.INT);
            type = Type.INT;
        } else if (lhs.type().equals(Type.DOUBLE)) {
            rhs.type().mustMatchExpected(line(), Type.DOUBLE);
            type = Type.DOUBLE;
        } else if (lhs.type().equals(Type.LONG)) {
            rhs.type().mustMatchExpected(line(), Type.LONG);
            type = Type.LONG;
        } else if (lhs.type().equals(Type.STRING)) {
            rhs = (new JStringConcatenationOp(line, lhs, rhs)).analyze(context);
            type = Type.STRING;
        } else {
            JAST.compilationUnit.reportSemanticError(line(),
                    "Invalid lhs type for +=: " + lhs.type());
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public void codegen(CLEmitter output) {
        //modifying codegen from JPlusAssignOp
        ((JLhs) lhs).codegenLoadLhsLvalue(output);
        if (lhs.type().equals(Type.STRING)) {
            rhs.codegen(output);
        } else if (lhs.type().equals(Type.DOUBLE)) {
            ((JLhs) lhs).codegenLoadLhsRvalue(output);
            rhs.codegen(output);
            output.addNoArgInstruction(DSUB);
        } else if (lhs.type().equals(Type.LONG)) {
            ((JLhs) lhs).codegenLoadLhsRvalue(output);
            rhs.codegen(output);
            output.addNoArgInstruction(LSUB);
        } else if (lhs.type().equals(Type.INT)){
            ((JLhs) lhs).codegenLoadLhsRvalue(output);
            rhs.codegen(output);
            output.addNoArgInstruction(ISUB);
        }
        if (!isStatementExpression) {
            ((JLhs) lhs).codegenDuplicateRvalue(output);
        }
        ((JLhs) lhs).codegenStore(output);
    }
}


/**
 * The AST node for a or-assign (|=) operation.
 */
class JOrAssignOp extends JAssignment {
    /**
     * Constructs the AST node for a or-assign (|=) operation.
     *
     * @param line line in which the assignment operation occurs in the source file.
     * @param lhs  the lhs operand.
     * @param rhs  the rhs operand.
     */
    public JOrAssignOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "|=", lhs, rhs);
    }

    /**
     * {@inheritDoc}
     */
    public JExpression analyze(Context context) {
        //Taken from JALefttShiftAssignOp
        if (!(lhs instanceof JLhs)) {
            JAST.compilationUnit.reportSemanticError(line(), "Illegal lhs for assignment");
        } else {
            lhs = (JExpression) ((JLhs) lhs).analyzeLhs(context);
        }
        rhs = (JExpression) rhs.analyze(context);

        if (lhs.type().equals(Type.INT)) {
            rhs.type().mustMatchExpected(line(), Type.INT);
            type = Type.INT;
        } else if (lhs.type().equals(Type.LONG)) {
            rhs.type().mustMatchExpected(line(), Type.LONG);
            type = Type.LONG;
        } else if (lhs.type().equals(Type.STRING)) {
            rhs = (new JStringConcatenationOp(line, lhs, rhs)).analyze(context);
            type = Type.STRING;
        } else {
            JAST.compilationUnit.reportSemanticError(line(),
                    "Invalid lhs type for +=: " + lhs.type());
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public void codegen(CLEmitter output) {
        //modifying codegen from JARighttShiftAssignOp
        ((JLhs) lhs).codegenLoadLhsLvalue(output);
        if (lhs.type().equals(Type.STRING)) {
            rhs.codegen(output);
        } else if (lhs.type().equals(Type.LONG)) {
            ((JLhs) lhs).codegenLoadLhsRvalue(output);
            rhs.codegen(output);
            output.addNoArgInstruction(LOR);
        } else if (lhs.type().equals(Type.INT)){
            ((JLhs) lhs).codegenLoadLhsRvalue(output);
            rhs.codegen(output);
            output.addNoArgInstruction(IOR);
        }
        if (!isStatementExpression) {
            ((JLhs) lhs).codegenDuplicateRvalue(output);
        }
        ((JLhs) lhs).codegenStore(output);
    }
}

/**
 * The AST node for a plus-assign (+=) operation.
 */
class JPlusAssignOp extends JAssignment {
    /**
     * Constructs the AST node for a plus-assign (+=) operation.
     *
     * @param line line in which the assignment operation occurs in the source file.
     * @param lhs  the lhs operand.
     * @param rhs  the rhs operand.
     */
    public JPlusAssignOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "+=", lhs, rhs);
    }

    /**
     * {@inheritDoc}
     */
    public JExpression analyze(Context context) {
        if (!(lhs instanceof JLhs)) {
            JAST.compilationUnit.reportSemanticError(line(), "Illegal lhs for assignment");
            return this;
        } else {
            lhs = (JExpression) ((JLhs) lhs).analyzeLhs(context);
        }
        rhs = (JExpression) rhs.analyze(context);
        if (lhs.type().equals(Type.INT)) {
            rhs.type().mustMatchExpected(line(), Type.INT);
            type = Type.INT;
            //MODIFIED TO ADD SUPPORT FOR LONG AND DOUBLE
        } else if (lhs.type().equals(Type.DOUBLE)) {
            rhs.type().mustMatchExpected(line(), Type.DOUBLE);
            type = Type.DOUBLE;
        } else if (lhs.type().equals(Type.LONG)) {
            rhs.type().mustMatchExpected(line(), Type.LONG);
            type = Type.LONG;
        } else if (lhs.type().equals(Type.STRING)) {
            rhs = (new JStringConcatenationOp(line, lhs, rhs)).analyze(context);
            type = Type.STRING;
        } else {
            JAST.compilationUnit.reportSemanticError(line(),
                    "Invalid lhs type for +=: " + lhs.type());
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public void codegen(CLEmitter output) {
        ((JLhs) lhs).codegenLoadLhsLvalue(output);
        if (lhs.type().equals(Type.STRING)) {
            rhs.codegen(output);
            //MODIFIED TO SUPPORT DOUBLE AND LONG
        } else if (lhs.type().equals(Type.DOUBLE)) {
            ((JLhs) lhs).codegenLoadLhsRvalue(output);
            rhs.codegen(output);
            output.addNoArgInstruction(DADD);
        } else if (lhs.type().equals(Type.LONG)) {
            ((JLhs) lhs).codegenLoadLhsRvalue(output);
            rhs.codegen(output);
            output.addNoArgInstruction(LADD);
        } else if (lhs.type().equals(Type.INT)){
            ((JLhs) lhs).codegenLoadLhsRvalue(output);
            rhs.codegen(output);
            output.addNoArgInstruction(IADD);
        }
        if (!isStatementExpression) {
            ((JLhs) lhs).codegenDuplicateRvalue(output);
        }
        ((JLhs) lhs).codegenStore(output);
    }
}

/**
 * The AST node for a rem-assign (%=) operation.
 */
class JRemAssignOp extends JAssignment {
    /**
     * Constructs the AST node for a rem-assign (%=) operation.
     *
     * @param line line in which the assignment operation occurs in the source file.
     * @param lhs  the lhs operand.
     * @param rhs  the rhs operand.
     */
    public JRemAssignOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "%=", lhs, rhs);
    }

    /**
     * {@inheritDoc}
     */
    public JExpression analyze(Context context) {
        //Taken from JAssignOp
        if (!(lhs instanceof JLhs)) {
            JAST.compilationUnit.reportSemanticError(line(), "Illegal lhs for assignment");
        } else {
            lhs = (JExpression) ((JLhs) lhs).analyzeLhs(context);
        }
        rhs = (JExpression) rhs.analyze(context);

        //taken inspiration from JPlusAssignOp and modified
        if (lhs.type().equals(Type.INT)) {
            rhs.type().mustMatchExpected(line(), Type.INT);
            type = Type.INT;
        } else if (lhs.type().equals(Type.DOUBLE)) {
            rhs.type().mustMatchExpected(line(), Type.DOUBLE);
            type = Type.DOUBLE;
        } else if (lhs.type().equals(Type.LONG)) {
            rhs.type().mustMatchExpected(line(), Type.LONG);
            type = Type.LONG;
        } else if (lhs.type().equals(Type.STRING)) {
            rhs = (new JStringConcatenationOp(line, lhs, rhs)).analyze(context);
            type = Type.STRING;
        } else {
            JAST.compilationUnit.reportSemanticError(line(),
                    "Invalid lhs type for +=: " + lhs.type());
        }
        return this;

    }

    /**
     * {@inheritDoc}
     */
    public void codegen(CLEmitter output) {
        //modifying codegen from JPlusAssignOp
        ((JLhs) lhs).codegenLoadLhsLvalue(output);
        if (lhs.type().equals(Type.STRING)) {
            rhs.codegen(output);
        } else if (lhs.type().equals(Type.DOUBLE)) {
            ((JLhs) lhs).codegenLoadLhsRvalue(output);
            rhs.codegen(output);
            output.addNoArgInstruction(DREM);
        } else if (lhs.type().equals(Type.LONG)) {
            ((JLhs) lhs).codegenLoadLhsRvalue(output);
            rhs.codegen(output);
            output.addNoArgInstruction(LREM);
        } else if (lhs.type().equals(Type.INT)){
            ((JLhs) lhs).codegenLoadLhsRvalue(output);
            rhs.codegen(output);
            output.addNoArgInstruction(IREM);
        }
        if (!isStatementExpression) {
            ((JLhs) lhs).codegenDuplicateRvalue(output);
        }
        ((JLhs) lhs).codegenStore(output);
    }
}



/**
 * The AST node for a star-assign (*=) operation.
 */
class JStarAssignOp extends JAssignment {
    /**
     * Constructs the AST node for a star-assign (*=) operation.
     *
     * @param line line in which the assignment operation occurs in the source file.
     * @param lhs  the lhs operand.
     * @param rhs  the rhs operand.
     */
    public JStarAssignOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "*=", lhs, rhs);
    }

    /**
     * {@inheritDoc}
     */
    public JExpression analyze(Context context) {

        //Taken from JAssignOp
        if (!(lhs instanceof JLhs)) {
            JAST.compilationUnit.reportSemanticError(line(), "Illegal lhs for assignment");
        } else {
            lhs = (JExpression) ((JLhs) lhs).analyzeLhs(context);
        }
        rhs = (JExpression) rhs.analyze(context);

        //taken inspiration from JPlusAssignOp and modified
        if (lhs.type().equals(Type.INT)) {
            rhs.type().mustMatchExpected(line(), Type.INT);
            type = Type.INT;
        } else if (lhs.type().equals(Type.DOUBLE)) {
            rhs.type().mustMatchExpected(line(), Type.DOUBLE);
            type = Type.DOUBLE;
        } else if (lhs.type().equals(Type.LONG)) {
            rhs.type().mustMatchExpected(line(), Type.LONG);
            type = Type.LONG;
        } else if (lhs.type().equals(Type.STRING)) {
            rhs = (new JStringConcatenationOp(line, lhs, rhs)).analyze(context);
            type = Type.STRING;
        } else {
            JAST.compilationUnit.reportSemanticError(line(),
                    "Invalid lhs type for +=: " + lhs.type());
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public void codegen(CLEmitter output) {
        //modifying codegen from JPlusAssignOp
        ((JLhs) lhs).codegenLoadLhsLvalue(output);
        if (lhs.type().equals(Type.STRING)) {
            rhs.codegen(output);
        } else if (lhs.type().equals(Type.DOUBLE)) {
            ((JLhs) lhs).codegenLoadLhsRvalue(output);
            rhs.codegen(output);
            output.addNoArgInstruction(DMUL);
        } else if (lhs.type().equals(Type.LONG)) {
            ((JLhs) lhs).codegenLoadLhsRvalue(output);
            rhs.codegen(output);
            output.addNoArgInstruction(LMUL);
        } else if (lhs.type().equals(Type.INT)){
            ((JLhs) lhs).codegenLoadLhsRvalue(output);
            rhs.codegen(output);
            output.addNoArgInstruction(IMUL);
        }
        if (!isStatementExpression) {
            ((JLhs) lhs).codegenDuplicateRvalue(output);
        }
        ((JLhs) lhs).codegenStore(output);
    }
}

/**
 * The AST node for a xor-assign (^=) operation.
 */
class JXorAssignOp extends JAssignment {
    /**
     * Constructs the AST node for a star-assign (^=) operation.
     *
     * @param line line in which the assignment operation occurs in the source file.
     * @param lhs  the lhs operand.
     * @param rhs  the rhs operand.
     */
    public JXorAssignOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "^=", lhs, rhs);
    }

    /**
     * {@inheritDoc}
     */
    public JExpression analyze(Context context) {
        //Taken from JALefttShiftAssignOp
        if (!(lhs instanceof JLhs)) {
            JAST.compilationUnit.reportSemanticError(line(), "Illegal lhs for assignment");
        } else {
            lhs = (JExpression) ((JLhs) lhs).analyzeLhs(context);
        }
        rhs = (JExpression) rhs.analyze(context);

        if (lhs.type().equals(Type.INT)) {
            rhs.type().mustMatchExpected(line(), Type.INT);
            type = Type.INT;
        } else if (lhs.type().equals(Type.LONG)) {
            rhs.type().mustMatchExpected(line(), Type.LONG);
            type = Type.LONG;
        } else if (lhs.type().equals(Type.STRING)) {
            rhs = (new JStringConcatenationOp(line, lhs, rhs)).analyze(context);
            type = Type.STRING;
        } else {
            JAST.compilationUnit.reportSemanticError(line(),
                    "Invalid lhs type for +=: " + lhs.type());
        }
        return this;

    }

    /**
     * {@inheritDoc}
     */
    public void codegen(CLEmitter output) {
        //modifying codegen from JARighttShiftAssignOp
        ((JLhs) lhs).codegenLoadLhsLvalue(output);
        if (lhs.type().equals(Type.STRING)) {
            rhs.codegen(output);
        } else if (lhs.type().equals(Type.LONG)) {
            ((JLhs) lhs).codegenLoadLhsRvalue(output);
            rhs.codegen(output);
            output.addNoArgInstruction(LXOR);
        } else if (lhs.type().equals(Type.INT)){
            ((JLhs) lhs).codegenLoadLhsRvalue(output);
            rhs.codegen(output);
            output.addNoArgInstruction(IXOR);
        }
        if (!isStatementExpression) {
            ((JLhs) lhs).codegenDuplicateRvalue(output);
        }
        ((JLhs) lhs).codegenStore(output);
    }
}
