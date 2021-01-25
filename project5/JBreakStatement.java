package jminusminus;

import static jminusminus.CLConstants.*;

public class JBreakStatement extends JStatement {


    private JExpression expr;
        /**
         * Construct an AST node for a break statement.
         *
         * @param line
         *            line in which the empty statement occurs in the source file.
         */

    protected JBreakStatement(int line) {
            super(line);
        }

        /**
         * @inheritDoc
         */

        public JBreakStatement analyze(Context context) {
            MethodContext methodContext = context.methodContext();
            if (methodContext.methodReturnType() == Type.CONSTRUCTOR) {
                if (expr != null) {
                    // Can't return a value from a constructor
                    JAST.compilationUnit.reportSemanticError(line(),
                            "cannot return a value from a constructor");
                }
            } else {
                // Must be a method
                Type returnType = methodContext.methodReturnType();
                methodContext.confirmMethodHasReturn();
                if (expr != null) {
                    if (returnType == Type.VOID) {
                        // Can't return a value from void method
                        JAST.compilationUnit.reportSemanticError(line(),
                                "cannot return a value from a void method");
                    } else {
                        // There's a (non-void) return expression.
                        // Its
                        // type must match the return type of the
                        // method
                        expr = expr.analyze(context);
                        expr.type().mustMatchExpected(line(), returnType);
                    }
                } else {
                    // The method better have void as return type
                    if (returnType != Type.VOID) {
                        JAST.compilationUnit.reportSemanticError(line(),
                                "missing return value");
                    }
                }
            }
            return this;
            
        }

        /**
         * @inheritDoc
         */

        public void codegen(CLEmitter output) {
            if (expr == null) {
                output.addNoArgInstruction(RETURN);
            } else {
                expr.codegen(output);
                if (expr.type() == Type.INT || expr.type() == Type.BOOLEAN || expr.type() == Type.CHAR) {
                    output.addNoArgInstruction(IRETURN);
                } else if (expr.type() == Type.DOUBLE){
                    output.addNoArgInstruction(DRETURN);
                } else if (expr.type() == Type.LONG) {
                    output.addNoArgInstruction(LRETURN);
                }else {
                    output.addNoArgInstruction(ARETURN);
                }
            }
        }

        /**
         * @inheritDoc
         */

        public void toJSON(JSONElement json) {
            JSONElement e = new JSONElement();
            json.addChild("JBreakStatement:" + line, e);
        }

    }