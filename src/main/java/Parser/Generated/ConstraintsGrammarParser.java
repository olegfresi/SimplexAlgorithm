// Generated from ConstraintsGrammar.g4 by ANTLR 4.13.2

package Parser.Generated;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class ConstraintsGrammarParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, ADD=3, SUB=4, LE=5, GE=6, EQ=7, LT=8, GT=9, VARIABLE=10, 
		NUMBER=11, NL=12, WS=13;
	public static final int
		RULE_file = 0, RULE_formula = 1, RULE_expr = 2, RULE_term = 3, RULE_relop = 4;
	private static String[] makeRuleNames() {
		return new String[] {
			"file", "formula", "expr", "term", "relop"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'('", "')'", "'+'", "'-'", "'<='", "'>='", "'='", "'<'", "'>'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, "ADD", "SUB", "LE", "GE", "EQ", "LT", "GT", "VARIABLE", 
			"NUMBER", "NL", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "ConstraintsGrammar.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public ConstraintsGrammarParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FileContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(ConstraintsGrammarParser.EOF, 0); }
		public List<TerminalNode> NL() { return getTokens(ConstraintsGrammarParser.NL); }
		public TerminalNode NL(int i) {
			return getToken(ConstraintsGrammarParser.NL, i);
		}
		public List<FormulaContext> formula() {
			return getRuleContexts(FormulaContext.class);
		}
		public FormulaContext formula(int i) {
			return getRuleContext(FormulaContext.class,i);
		}
		public FileContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_file; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ConstraintsGrammarListener ) ((ConstraintsGrammarListener)listener).enterFile(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ConstraintsGrammarListener ) ((ConstraintsGrammarListener)listener).exitFile(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ConstraintsGrammarVisitor ) return ((ConstraintsGrammarVisitor<? extends T>)visitor).visitFile(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FileContext file() throws RecognitionException {
		FileContext _localctx = new FileContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_file);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(13);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(10);
					match(NL);
					}
					} 
				}
				setState(15);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
			}
			setState(28);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 3090L) != 0)) {
				{
				setState(16);
				formula();
				setState(25);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(18); 
						_errHandler.sync(this);
						_la = _input.LA(1);
						do {
							{
							{
							setState(17);
							match(NL);
							}
							}
							setState(20); 
							_errHandler.sync(this);
							_la = _input.LA(1);
						} while ( _la==NL );
						setState(22);
						formula();
						}
						} 
					}
					setState(27);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
				}
				}
			}

			setState(33);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NL) {
				{
				{
				setState(30);
				match(NL);
				}
				}
				setState(35);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(36);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FormulaContext extends ParserRuleContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public RelopContext relop() {
			return getRuleContext(RelopContext.class,0);
		}
		public FormulaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_formula; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ConstraintsGrammarListener ) ((ConstraintsGrammarListener)listener).enterFormula(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ConstraintsGrammarListener ) ((ConstraintsGrammarListener)listener).exitFormula(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ConstraintsGrammarVisitor ) return ((ConstraintsGrammarVisitor<? extends T>)visitor).visitFormula(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FormulaContext formula() throws RecognitionException {
		FormulaContext _localctx = new FormulaContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_formula);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(38);
			expr();
			setState(39);
			relop();
			setState(40);
			expr();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExprContext extends ParserRuleContext {
		public List<TermContext> term() {
			return getRuleContexts(TermContext.class);
		}
		public TermContext term(int i) {
			return getRuleContext(TermContext.class,i);
		}
		public List<TerminalNode> ADD() { return getTokens(ConstraintsGrammarParser.ADD); }
		public TerminalNode ADD(int i) {
			return getToken(ConstraintsGrammarParser.ADD, i);
		}
		public List<TerminalNode> SUB() { return getTokens(ConstraintsGrammarParser.SUB); }
		public TerminalNode SUB(int i) {
			return getToken(ConstraintsGrammarParser.SUB, i);
		}
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ConstraintsGrammarListener ) ((ConstraintsGrammarListener)listener).enterExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ConstraintsGrammarListener ) ((ConstraintsGrammarListener)listener).exitExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ConstraintsGrammarVisitor ) return ((ConstraintsGrammarVisitor<? extends T>)visitor).visitExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		ExprContext _localctx = new ExprContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_expr);
		int _la;
		try {
			setState(63);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(42);
				term();
				setState(49);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==ADD || _la==SUB) {
					{
					setState(47);
					_errHandler.sync(this);
					switch (_input.LA(1)) {
					case ADD:
						{
						setState(43);
						match(ADD);
						setState(44);
						term();
						}
						break;
					case SUB:
						{
						setState(45);
						match(SUB);
						setState(46);
						term();
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					}
					setState(51);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(52);
				match(SUB);
				setState(53);
				term();
				setState(60);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==ADD || _la==SUB) {
					{
					setState(58);
					_errHandler.sync(this);
					switch (_input.LA(1)) {
					case ADD:
						{
						setState(54);
						match(ADD);
						setState(55);
						term();
						}
						break;
					case SUB:
						{
						setState(56);
						match(SUB);
						setState(57);
						term();
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					}
					setState(62);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TermContext extends ParserRuleContext {
		public TermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_term; }
	 
		public TermContext() { }
		public void copyFrom(TermContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CoeffVarContext extends TermContext {
		public TerminalNode NUMBER() { return getToken(ConstraintsGrammarParser.NUMBER, 0); }
		public TerminalNode VARIABLE() { return getToken(ConstraintsGrammarParser.VARIABLE, 0); }
		public CoeffVarContext(TermContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ConstraintsGrammarListener ) ((ConstraintsGrammarListener)listener).enterCoeffVar(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ConstraintsGrammarListener ) ((ConstraintsGrammarListener)listener).exitCoeffVar(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ConstraintsGrammarVisitor ) return ((ConstraintsGrammarVisitor<? extends T>)visitor).visitCoeffVar(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NumberContext extends TermContext {
		public TerminalNode NUMBER() { return getToken(ConstraintsGrammarParser.NUMBER, 0); }
		public NumberContext(TermContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ConstraintsGrammarListener ) ((ConstraintsGrammarListener)listener).enterNumber(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ConstraintsGrammarListener ) ((ConstraintsGrammarListener)listener).exitNumber(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ConstraintsGrammarVisitor ) return ((ConstraintsGrammarVisitor<? extends T>)visitor).visitNumber(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ParensContext extends TermContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ParensContext(TermContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ConstraintsGrammarListener ) ((ConstraintsGrammarListener)listener).enterParens(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ConstraintsGrammarListener ) ((ConstraintsGrammarListener)listener).exitParens(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ConstraintsGrammarVisitor ) return ((ConstraintsGrammarVisitor<? extends T>)visitor).visitParens(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class VarContext extends TermContext {
		public TerminalNode VARIABLE() { return getToken(ConstraintsGrammarParser.VARIABLE, 0); }
		public VarContext(TermContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ConstraintsGrammarListener ) ((ConstraintsGrammarListener)listener).enterVar(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ConstraintsGrammarListener ) ((ConstraintsGrammarListener)listener).exitVar(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ConstraintsGrammarVisitor ) return ((ConstraintsGrammarVisitor<? extends T>)visitor).visitVar(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NegTermContext extends TermContext {
		public TerminalNode SUB() { return getToken(ConstraintsGrammarParser.SUB, 0); }
		public TermContext term() {
			return getRuleContext(TermContext.class,0);
		}
		public NegTermContext(TermContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ConstraintsGrammarListener ) ((ConstraintsGrammarListener)listener).enterNegTerm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ConstraintsGrammarListener ) ((ConstraintsGrammarListener)listener).exitNegTerm(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ConstraintsGrammarVisitor ) return ((ConstraintsGrammarVisitor<? extends T>)visitor).visitNegTerm(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TermContext term() throws RecognitionException {
		TermContext _localctx = new TermContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_term);
		try {
			setState(75);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				_localctx = new CoeffVarContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(65);
				match(NUMBER);
				setState(66);
				match(VARIABLE);
				}
				break;
			case 2:
				_localctx = new VarContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(67);
				match(VARIABLE);
				}
				break;
			case 3:
				_localctx = new NumberContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(68);
				match(NUMBER);
				}
				break;
			case 4:
				_localctx = new NegTermContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(69);
				match(SUB);
				setState(70);
				term();
				}
				break;
			case 5:
				_localctx = new ParensContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(71);
				match(T__0);
				setState(72);
				expr();
				setState(73);
				match(T__1);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class RelopContext extends ParserRuleContext {
		public TerminalNode LE() { return getToken(ConstraintsGrammarParser.LE, 0); }
		public TerminalNode GE() { return getToken(ConstraintsGrammarParser.GE, 0); }
		public TerminalNode EQ() { return getToken(ConstraintsGrammarParser.EQ, 0); }
		public TerminalNode LT() { return getToken(ConstraintsGrammarParser.LT, 0); }
		public TerminalNode GT() { return getToken(ConstraintsGrammarParser.GT, 0); }
		public RelopContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_relop; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ConstraintsGrammarListener ) ((ConstraintsGrammarListener)listener).enterRelop(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ConstraintsGrammarListener ) ((ConstraintsGrammarListener)listener).exitRelop(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ConstraintsGrammarVisitor ) return ((ConstraintsGrammarVisitor<? extends T>)visitor).visitRelop(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RelopContext relop() throws RecognitionException {
		RelopContext _localctx = new RelopContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_relop);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(77);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 992L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\u0004\u0001\rP\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0001"+
		"\u0000\u0005\u0000\f\b\u0000\n\u0000\f\u0000\u000f\t\u0000\u0001\u0000"+
		"\u0001\u0000\u0004\u0000\u0013\b\u0000\u000b\u0000\f\u0000\u0014\u0001"+
		"\u0000\u0005\u0000\u0018\b\u0000\n\u0000\f\u0000\u001b\t\u0000\u0003\u0000"+
		"\u001d\b\u0000\u0001\u0000\u0005\u0000 \b\u0000\n\u0000\f\u0000#\t\u0000"+
		"\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0005\u0002"+
		"0\b\u0002\n\u0002\f\u00023\t\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0005\u0002;\b\u0002\n\u0002\f\u0002"+
		">\t\u0002\u0003\u0002@\b\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0003\u0003L\b\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0000"+
		"\u0000\u0005\u0000\u0002\u0004\u0006\b\u0000\u0001\u0001\u0000\u0005\t"+
		"X\u0000\r\u0001\u0000\u0000\u0000\u0002&\u0001\u0000\u0000\u0000\u0004"+
		"?\u0001\u0000\u0000\u0000\u0006K\u0001\u0000\u0000\u0000\bM\u0001\u0000"+
		"\u0000\u0000\n\f\u0005\f\u0000\u0000\u000b\n\u0001\u0000\u0000\u0000\f"+
		"\u000f\u0001\u0000\u0000\u0000\r\u000b\u0001\u0000\u0000\u0000\r\u000e"+
		"\u0001\u0000\u0000\u0000\u000e\u001c\u0001\u0000\u0000\u0000\u000f\r\u0001"+
		"\u0000\u0000\u0000\u0010\u0019\u0003\u0002\u0001\u0000\u0011\u0013\u0005"+
		"\f\u0000\u0000\u0012\u0011\u0001\u0000\u0000\u0000\u0013\u0014\u0001\u0000"+
		"\u0000\u0000\u0014\u0012\u0001\u0000\u0000\u0000\u0014\u0015\u0001\u0000"+
		"\u0000\u0000\u0015\u0016\u0001\u0000\u0000\u0000\u0016\u0018\u0003\u0002"+
		"\u0001\u0000\u0017\u0012\u0001\u0000\u0000\u0000\u0018\u001b\u0001\u0000"+
		"\u0000\u0000\u0019\u0017\u0001\u0000\u0000\u0000\u0019\u001a\u0001\u0000"+
		"\u0000\u0000\u001a\u001d\u0001\u0000\u0000\u0000\u001b\u0019\u0001\u0000"+
		"\u0000\u0000\u001c\u0010\u0001\u0000\u0000\u0000\u001c\u001d\u0001\u0000"+
		"\u0000\u0000\u001d!\u0001\u0000\u0000\u0000\u001e \u0005\f\u0000\u0000"+
		"\u001f\u001e\u0001\u0000\u0000\u0000 #\u0001\u0000\u0000\u0000!\u001f"+
		"\u0001\u0000\u0000\u0000!\"\u0001\u0000\u0000\u0000\"$\u0001\u0000\u0000"+
		"\u0000#!\u0001\u0000\u0000\u0000$%\u0005\u0000\u0000\u0001%\u0001\u0001"+
		"\u0000\u0000\u0000&\'\u0003\u0004\u0002\u0000\'(\u0003\b\u0004\u0000("+
		")\u0003\u0004\u0002\u0000)\u0003\u0001\u0000\u0000\u0000*1\u0003\u0006"+
		"\u0003\u0000+,\u0005\u0003\u0000\u0000,0\u0003\u0006\u0003\u0000-.\u0005"+
		"\u0004\u0000\u0000.0\u0003\u0006\u0003\u0000/+\u0001\u0000\u0000\u0000"+
		"/-\u0001\u0000\u0000\u000003\u0001\u0000\u0000\u00001/\u0001\u0000\u0000"+
		"\u000012\u0001\u0000\u0000\u00002@\u0001\u0000\u0000\u000031\u0001\u0000"+
		"\u0000\u000045\u0005\u0004\u0000\u00005<\u0003\u0006\u0003\u000067\u0005"+
		"\u0003\u0000\u00007;\u0003\u0006\u0003\u000089\u0005\u0004\u0000\u0000"+
		"9;\u0003\u0006\u0003\u0000:6\u0001\u0000\u0000\u0000:8\u0001\u0000\u0000"+
		"\u0000;>\u0001\u0000\u0000\u0000<:\u0001\u0000\u0000\u0000<=\u0001\u0000"+
		"\u0000\u0000=@\u0001\u0000\u0000\u0000><\u0001\u0000\u0000\u0000?*\u0001"+
		"\u0000\u0000\u0000?4\u0001\u0000\u0000\u0000@\u0005\u0001\u0000\u0000"+
		"\u0000AB\u0005\u000b\u0000\u0000BL\u0005\n\u0000\u0000CL\u0005\n\u0000"+
		"\u0000DL\u0005\u000b\u0000\u0000EF\u0005\u0004\u0000\u0000FL\u0003\u0006"+
		"\u0003\u0000GH\u0005\u0001\u0000\u0000HI\u0003\u0004\u0002\u0000IJ\u0005"+
		"\u0002\u0000\u0000JL\u0001\u0000\u0000\u0000KA\u0001\u0000\u0000\u0000"+
		"KC\u0001\u0000\u0000\u0000KD\u0001\u0000\u0000\u0000KE\u0001\u0000\u0000"+
		"\u0000KG\u0001\u0000\u0000\u0000L\u0007\u0001\u0000\u0000\u0000MN\u0007"+
		"\u0000\u0000\u0000N\t\u0001\u0000\u0000\u0000\u000b\r\u0014\u0019\u001c"+
		"!/1:<?K";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}