/* Generated By:JavaCC: Do not edit this line. GeneratedFormulaParser.java */
package org.pentaho.reporting.libraries.formula.parser;

import org.pentaho.reporting.libraries.formula.lvalues.ContextLookup;
import org.pentaho.reporting.libraries.formula.lvalues.DefaultDataTable;
import org.pentaho.reporting.libraries.formula.lvalues.FormulaFunction;
import org.pentaho.reporting.libraries.formula.lvalues.LValue;
import org.pentaho.reporting.libraries.formula.lvalues.ParsePosition;
import org.pentaho.reporting.libraries.formula.lvalues.PostfixTerm;
import org.pentaho.reporting.libraries.formula.lvalues.PrefixTerm;
import org.pentaho.reporting.libraries.formula.lvalues.StaticValue;
import org.pentaho.reporting.libraries.formula.lvalues.Term;
import org.pentaho.reporting.libraries.formula.operators.InfixOperator;
import org.pentaho.reporting.libraries.formula.operators.OperatorFactory;
import org.pentaho.reporting.libraries.formula.operators.PostfixOperator;
import org.pentaho.reporting.libraries.formula.operators.PrefixOperator;
import org.pentaho.reporting.libraries.formula.typing.coretypes.NumberType;
import org.pentaho.reporting.libraries.formula.typing.coretypes.TextType;

import java.math.BigDecimal;
import java.util.ArrayList;

public abstract class GeneratedFormulaParser implements GeneratedFormulaParserConstants {

  protected GeneratedFormulaParser() {
  }

  protected abstract OperatorFactory getOperatorFactory();

  protected ParsePosition createPosition( Token token ) {
    return new ParsePosition
      ( token.beginLine, token.beginColumn, token.endLine, token.endColumn );
  }

  final public LValue getExpression() throws ParseException {
    LValue retval = null;
    Term term = null;
    retval = getLValue();
    switch( jj_nt.kind ) {
      case PLUS:
      case MINUS:
      case MULT:
      case DIV:
      case POW:
      case EQUALS:
      case NOT_EQUALS:
      case LT_EQUALS:
      case GT_EQUALS:
      case LT:
      case GT:
      case CONCAT:
        term = startTail( new Term( retval ) );
        label_1:
        while ( true ) {
          switch( jj_nt.kind ) {
            case PLUS:
            case MINUS:
            case MULT:
            case DIV:
            case POW:
            case EQUALS:
            case NOT_EQUALS:
            case LT_EQUALS:
            case GT_EQUALS:
            case LT:
            case GT:
            case CONCAT:
              ;
              break;
            default:
              jj_la1[ 0 ] = jj_gen;
              break label_1;
          }
          term = startTail( term );
        }
        break;
      default:
        jj_la1[ 1 ] = jj_gen;
        ;
    }
    if ( term != null ) {
      {
        if ( true ) {
          return term;
        }
      }
    }
    {
      if ( true ) {
        return retval;
      }
    }
    throw new Error( "Missing return statement in function" );
  }

  final public Term startTail( LValue retval ) throws ParseException {
    LValue val = null;
    InfixOperator op = null;
    Term ex = null;
    op = getInfixOperator();
    val = getLValue();
    if ( retval instanceof Term ) {
      ex = (Term) retval;
    } else {
      ex = new Term( retval );
    }
    ex.add( op, val );

    {
      if ( true ) {
        return ex;
      }
    }
    throw new Error( "Missing return statement in function" );
  }

  final public LValue getLValue() throws ParseException {
    Token value = null;
    LValue retval = null;
    PrefixOperator prefixOp = null;
    PostfixOperator postfixOp = null;
    switch( jj_nt.kind ) {
      case PLUS:
      case MINUS:
        prefixOp = getPrefixOperator();
        break;
      default:
        jj_la1[ 2 ] = jj_gen;
        ;
    }
    switch( jj_nt.kind ) {
      case COLUMN_LOOKUP:
        value = jj_consume_token( COLUMN_LOOKUP );
        retval = new ContextLookup( ParserTools.stripReferenceQuote( value.image ), createPosition( value ) );
        break;
      case STRING_LITERAL:
        value = jj_consume_token( STRING_LITERAL );
        retval = new StaticValue( ParserTools.stripQuote( value.image ), TextType.TYPE, createPosition( value ) );
        break;
      case UNSIGNED_NUMERIC_LITERAL:
        value = jj_consume_token( UNSIGNED_NUMERIC_LITERAL );
        retval = new StaticValue( new BigDecimal( value.image ), NumberType.GENERIC_NUMBER, createPosition( value ) );
        break;
      case UNSIGNED_INTEGER:
        value = jj_consume_token( UNSIGNED_INTEGER );
        retval = new StaticValue( new BigDecimal( value.image ), NumberType.GENERIC_NUMBER, createPosition( value ) );
        break;
      case NULL:
        jj_consume_token( NULL );
        retval = new StaticValue( null, createPosition( value ) );
        break;
      case L_BRACE:
        jj_consume_token( L_BRACE );
        retval = parseArray();
        jj_consume_token( R_BRACE );
        break;
      case IDENTIFIER:
        value = jj_consume_token( IDENTIFIER );
        jj_consume_token( L_PAREN );
        retval = parseFunction( value.image, createPosition( value ) );
        jj_consume_token( R_PAREN );
        break;
      case L_PAREN:
        jj_consume_token( L_PAREN );
        retval = getExpression();
        jj_consume_token( R_PAREN );
        if ( retval instanceof Term == false ) {
          retval = new Term( retval );
        }
        break;
      default:
        jj_la1[ 3 ] = jj_gen;
        jj_consume_token( -1 );
        throw new ParseException();
    }
    switch( jj_nt.kind ) {
      case PERCENT:
        postfixOp = getPostfixOperator();
        break;
      default:
        jj_la1[ 4 ] = jj_gen;
        ;
    }
    if ( postfixOp != null ) {
      retval = new PostfixTerm( retval, postfixOp );
    }
    if ( prefixOp != null ) {
      retval = new PrefixTerm( prefixOp, retval );
    }
    {
      if ( true ) {
        return retval;
      }
    }
    throw new Error( "Missing return statement in function" );
  }

  final public LValue parseArray() throws ParseException {
    ArrayList rows = new ArrayList();
    LValue[] row = null;
    switch( jj_nt.kind ) {
      case UNSIGNED_INTEGER:
      case L_PAREN:
      case L_BRACE:
      case PLUS:
      case MINUS:
      case IDENTIFIER:
      case COLUMN_LOOKUP:
      case STRING_LITERAL:
      case UNSIGNED_NUMERIC_LITERAL:
      case NULL:
        row = parseRow();
        rows.add( row );
        break;
      default:
        jj_la1[ 5 ] = jj_gen;
        ;
    }
    label_2:
    while ( true ) {
      switch( jj_nt.kind ) {
        case PIPE:
          ;
          break;
        default:
          jj_la1[ 6 ] = jj_gen;
          break label_2;
      }
      jj_consume_token( PIPE );
      row = parseRow();
      // should we check here for column count equality to the first row column count?
      // or do we give this responsability to a DefaultDataTable constructor?
      rows.add( row );
    }
    LValue[][] table = (LValue[][]) rows.toArray( new LValue[ rows.size() ][] );
    {
      if ( true ) {
        return new DefaultDataTable( table );
      }
    }
    throw new Error( "Missing return statement in function" );
  }

  final public LValue[] parseRow() throws ParseException {
    ArrayList cols = new ArrayList();
    ;
    LValue column = null;
    column = getExpression();
    cols.add( column );
    label_3:
    while ( true ) {
      switch( jj_nt.kind ) {
        case SEMICOLON:
          ;
          break;
        default:
          jj_la1[ 7 ] = jj_gen;
          break label_3;
      }
      jj_consume_token( SEMICOLON );
      column = getExpression();
      cols.add( column );
    }
    {
      if ( true ) {
        return (LValue[]) cols.toArray( new LValue[ cols.size() ] );
      }
    }
    throw new Error( "Missing return statement in function" );
  }

  final public LValue parseFunction( String name, ParsePosition parsePosition ) throws ParseException {
    ArrayList params = new ArrayList();
    LValue parameter = null;
    Token value = null;
    boolean parameterExpected = false;
    switch( jj_nt.kind ) {
      case UNSIGNED_INTEGER:
      case SEMICOLON:
      case L_PAREN:
      case L_BRACE:
      case PLUS:
      case MINUS:
      case IDENTIFIER:
      case COLUMN_LOOKUP:
      case STRING_LITERAL:
      case UNSIGNED_NUMERIC_LITERAL:
      case NULL:
        switch( jj_nt.kind ) {
          case SEMICOLON:
            value = jj_consume_token( SEMICOLON );
            parameterExpected = false;
            params.add( new StaticValue( null, createPosition( value ) ) );
            break;
          case UNSIGNED_INTEGER:
          case L_PAREN:
          case L_BRACE:
          case PLUS:
          case MINUS:
          case IDENTIFIER:
          case COLUMN_LOOKUP:
          case STRING_LITERAL:
          case UNSIGNED_NUMERIC_LITERAL:
          case NULL:
            parameter = getExpression();
            parameterExpected = false;
            params.add( parameter );
            break;
          default:
            jj_la1[ 8 ] = jj_gen;
            jj_consume_token( -1 );
            throw new ParseException();
        }
        label_4:
        while ( true ) {
          switch( jj_nt.kind ) {
            case SEMICOLON:
              ;
              break;
            default:
              jj_la1[ 9 ] = jj_gen;
              break label_4;
          }
          value = jj_consume_token( SEMICOLON );
          if ( parameterExpected == true ) {
            params.add( new StaticValue( null, createPosition( value ) ) );
          }
          parameterExpected = true;
          switch( jj_nt.kind ) {
            case UNSIGNED_INTEGER:
            case L_PAREN:
            case L_BRACE:
            case PLUS:
            case MINUS:
            case IDENTIFIER:
            case COLUMN_LOOKUP:
            case STRING_LITERAL:
            case UNSIGNED_NUMERIC_LITERAL:
            case NULL:
              parameter = getExpression();
              params.add( parameter );
              parameterExpected = false;
              break;
            default:
              jj_la1[ 10 ] = jj_gen;
              ;
          }
        }
        break;
      default:
        jj_la1[ 11 ] = jj_gen;
        ;
    }
    if ( parameterExpected == true ) {
      params.add( new StaticValue( null, createPosition( value ) ) );
    }

    if ( params == null ) {
      {
        if ( true ) {
          return new FormulaFunction( name, new LValue[ 0 ], parsePosition );
        }
      }
    }

    LValue[] paramVals = (LValue[]) params.toArray( new LValue[ params.size() ] );
    {
      if ( true ) {
        return new FormulaFunction( name, paramVals, parsePosition );
      }
    }
    throw new Error( "Missing return statement in function" );
  }

  final public PrefixOperator getPrefixOperator() throws ParseException {
    Token value = null;
    switch( jj_nt.kind ) {
      case PLUS:
        value = jj_consume_token( PLUS );
        break;
      case MINUS:
        value = jj_consume_token( MINUS );
        break;
      default:
        jj_la1[ 12 ] = jj_gen;
        jj_consume_token( -1 );
        throw new ParseException();
    }
    {
      if ( true ) {
        return getOperatorFactory().createPrefixOperator( value.image );
      }
    }
    throw new Error( "Missing return statement in function" );
  }

  final public PostfixOperator getPostfixOperator() throws ParseException {
    Token value = null;
    value = jj_consume_token( PERCENT );
    {
      if ( true ) {
        return getOperatorFactory().createPostfixOperator( value.image );
      }
    }
    throw new Error( "Missing return statement in function" );
  }

  final public InfixOperator getInfixOperator() throws ParseException {
    InfixOperator op = null;
    Token value = null;
    switch( jj_nt.kind ) {
      case PLUS:
        value = jj_consume_token( PLUS );
        break;
      case MINUS:
        value = jj_consume_token( MINUS );
        break;
      case MULT:
        value = jj_consume_token( MULT );
        break;
      case DIV:
        value = jj_consume_token( DIV );
        break;
      case POW:
        value = jj_consume_token( POW );
        break;
      case EQUALS:
        value = jj_consume_token( EQUALS );
        break;
      case NOT_EQUALS:
        value = jj_consume_token( NOT_EQUALS );
        break;
      case LT_EQUALS:
        value = jj_consume_token( LT_EQUALS );
        break;
      case GT_EQUALS:
        value = jj_consume_token( GT_EQUALS );
        break;
      case LT:
        value = jj_consume_token( LT );
        break;
      case GT:
        value = jj_consume_token( GT );
        break;
      case CONCAT:
        value = jj_consume_token( CONCAT );
        break;
      default:
        jj_la1[ 13 ] = jj_gen;
        jj_consume_token( -1 );
        throw new ParseException();
    }
    {
      if ( true ) {
        return getOperatorFactory().createInfixOperator( value.image );
      }
    }
    throw new Error( "Missing return statement in function" );
  }

  public GeneratedFormulaParserTokenManager token_source;
  JavaCharStream jj_input_stream;
  public Token token, jj_nt;
  private int jj_gen;
  final private int[] jj_la1 = new int[ 14 ];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;

  static {
    jj_la1_0();
    jj_la1_1();
  }

  private static void jj_la1_0() {
    jj_la1_0 =
      new int[] { 0xf8000000, 0xf8000000, 0x18000000, 0x1080100, 0x0, 0x19080100, 0x4000000, 0x40000, 0x190c0100,
        0x40000, 0x19080100, 0x190c0100, 0x18000000, 0xf8000000, };
  }

  private static void jj_la1_1() {
    jj_la1_1 =
      new int[] { 0x7f, 0x7f, 0x0, 0x10f00, 0x80, 0x10f00, 0x0, 0x0, 0x10f00, 0x0, 0x10f00, 0x10f00, 0x0, 0x7f, };
  }

  public GeneratedFormulaParser( java.io.InputStream stream ) {
    jj_input_stream = new JavaCharStream( stream, 1, 1 );
    token_source = new GeneratedFormulaParserTokenManager( jj_input_stream );
    token = new Token();
    token.next = jj_nt = token_source.getNextToken();
    jj_gen = 0;
    for ( int i = 0; i < 14; i++ ) {
      jj_la1[ i ] = -1;
    }
  }

  public void ReInit( java.io.InputStream stream ) {
    jj_input_stream.ReInit( stream, 1, 1 );
    token_source.ReInit( jj_input_stream );
    token = new Token();
    token.next = jj_nt = token_source.getNextToken();
    jj_gen = 0;
    for ( int i = 0; i < 14; i++ ) {
      jj_la1[ i ] = -1;
    }
  }

  public GeneratedFormulaParser( java.io.Reader stream ) {
    jj_input_stream = new JavaCharStream( stream, 1, 1 );
    token_source = new GeneratedFormulaParserTokenManager( jj_input_stream );
    token = new Token();
    token.next = jj_nt = token_source.getNextToken();
    jj_gen = 0;
    for ( int i = 0; i < 14; i++ ) {
      jj_la1[ i ] = -1;
    }
  }

  public void ReInit( java.io.Reader stream ) {
    jj_input_stream.ReInit( stream, 1, 1 );
    token_source.ReInit( jj_input_stream );
    token = new Token();
    token.next = jj_nt = token_source.getNextToken();
    jj_gen = 0;
    for ( int i = 0; i < 14; i++ ) {
      jj_la1[ i ] = -1;
    }
  }

  public GeneratedFormulaParser( GeneratedFormulaParserTokenManager tm ) {
    token_source = tm;
    token = new Token();
    token.next = jj_nt = token_source.getNextToken();
    jj_gen = 0;
    for ( int i = 0; i < 14; i++ ) {
      jj_la1[ i ] = -1;
    }
  }

  public void ReInit( GeneratedFormulaParserTokenManager tm ) {
    token_source = tm;
    token = new Token();
    token.next = jj_nt = token_source.getNextToken();
    jj_gen = 0;
    for ( int i = 0; i < 14; i++ ) {
      jj_la1[ i ] = -1;
    }
  }

  final private Token jj_consume_token( int kind ) throws ParseException {
    Token oldToken = token;
    if ( ( token = jj_nt ).next != null ) {
      jj_nt = jj_nt.next;
    } else {
      jj_nt = jj_nt.next = token_source.getNextToken();
    }
    if ( token.kind == kind ) {
      jj_gen++;
      return token;
    }
    jj_nt = token;
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }

  final public Token getNextToken() {
    if ( ( token = jj_nt ).next != null ) {
      jj_nt = jj_nt.next;
    } else {
      jj_nt = jj_nt.next = token_source.getNextToken();
    }
    jj_gen++;
    return token;
  }

  final public Token getToken( int index ) {
    Token t = token;
    for ( int i = 0; i < index; i++ ) {
      if ( t.next != null ) {
        t = t.next;
      } else {
        t = t.next = token_source.getNextToken();
      }
    }
    return t;
  }

  private java.util.Vector jj_expentries = new java.util.Vector();
  private int[] jj_expentry;
  private int jj_kind = -1;

  public ParseException generateParseException() {
    jj_expentries.removeAllElements();
    boolean[] la1tokens = new boolean[ 49 ];
    for ( int i = 0; i < 49; i++ ) {
      la1tokens[ i ] = false;
    }
    if ( jj_kind >= 0 ) {
      la1tokens[ jj_kind ] = true;
      jj_kind = -1;
    }
    for ( int i = 0; i < 14; i++ ) {
      if ( jj_la1[ i ] == jj_gen ) {
        for ( int j = 0; j < 32; j++ ) {
          if ( ( jj_la1_0[ i ] & ( 1 << j ) ) != 0 ) {
            la1tokens[ j ] = true;
          }
          if ( ( jj_la1_1[ i ] & ( 1 << j ) ) != 0 ) {
            la1tokens[ 32 + j ] = true;
          }
        }
      }
    }
    for ( int i = 0; i < 49; i++ ) {
      if ( la1tokens[ i ] ) {
        jj_expentry = new int[ 1 ];
        jj_expentry[ 0 ] = i;
        jj_expentries.addElement( jj_expentry );
      }
    }
    int[][] exptokseq = new int[ jj_expentries.size() ][];
    for ( int i = 0; i < jj_expentries.size(); i++ ) {
      exptokseq[ i ] = (int[]) jj_expentries.elementAt( i );
    }
    return new ParseException( token, exptokseq, tokenImage );
  }

  final public void enable_tracing() {
  }

  final public void disable_tracing() {
  }

}
