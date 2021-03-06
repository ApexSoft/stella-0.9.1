/*
* generated by Xtext
*/
package org.eclipse.papyrus.uml.textedit.parameter.xtext.ui.contentassist.antlr;

import java.util.Collection;
import java.util.Map;
import java.util.HashMap;

import org.antlr.runtime.RecognitionException;
import org.eclipse.xtext.AbstractElement;
import org.eclipse.xtext.ui.editor.contentassist.antlr.AbstractContentAssistParser;
import org.eclipse.xtext.ui.editor.contentassist.antlr.FollowElement;
import org.eclipse.xtext.ui.editor.contentassist.antlr.internal.AbstractInternalContentAssistParser;

import com.google.inject.Inject;

import org.eclipse.papyrus.uml.textedit.parameter.xtext.services.UmlParameterGrammarAccess;

public class UmlParameterParser extends AbstractContentAssistParser {
	
	@Inject
	private UmlParameterGrammarAccess grammarAccess;
	
	private Map<AbstractElement, String> nameMappings;
	
	@Override
	protected org.eclipse.papyrus.uml.textedit.parameter.xtext.ui.contentassist.antlr.internal.InternalUmlParameterParser createParser() {
		org.eclipse.papyrus.uml.textedit.parameter.xtext.ui.contentassist.antlr.internal.InternalUmlParameterParser result = new org.eclipse.papyrus.uml.textedit.parameter.xtext.ui.contentassist.antlr.internal.InternalUmlParameterParser(null);
		result.setGrammarAccess(grammarAccess);
		return result;
	}
	
	@Override
	protected String getRuleName(AbstractElement element) {
		if (nameMappings == null) {
			nameMappings = new HashMap<AbstractElement, String>() {
				private static final long serialVersionUID = 1L;
				{
					put(grammarAccess.getParameterRuleAccess().getAlternatives_4(), "rule__ParameterRule__Alternatives_4");
					put(grammarAccess.getUnlimitedLiteralAccess().getAlternatives(), "rule__UnlimitedLiteral__Alternatives");
					put(grammarAccess.getModifierKindAccess().getAlternatives(), "rule__ModifierKind__Alternatives");
					put(grammarAccess.getEffectKindAccess().getAlternatives(), "rule__EffectKind__Alternatives");
					put(grammarAccess.getVisibilityKindAccess().getAlternatives(), "rule__VisibilityKind__Alternatives");
					put(grammarAccess.getDirectionAccess().getAlternatives(), "rule__Direction__Alternatives");
					put(grammarAccess.getParameterRuleAccess().getGroup(), "rule__ParameterRule__Group__0");
					put(grammarAccess.getModifiersRuleAccess().getGroup(), "rule__ModifiersRule__Group__0");
					put(grammarAccess.getModifiersRuleAccess().getGroup_2(), "rule__ModifiersRule__Group_2__0");
					put(grammarAccess.getEffectRuleAccess().getGroup(), "rule__EffectRule__Group__0");
					put(grammarAccess.getQualifiedNameAccess().getGroup(), "rule__QualifiedName__Group__0");
					put(grammarAccess.getTypeRuleAccess().getGroup(), "rule__TypeRule__Group__0");
					put(grammarAccess.getMultiplicityRuleAccess().getGroup(), "rule__MultiplicityRule__Group__0");
					put(grammarAccess.getMultiplicityRuleAccess().getGroup_2(), "rule__MultiplicityRule__Group_2__0");
					put(grammarAccess.getParameterRuleAccess().getVisibilityAssignment_0(), "rule__ParameterRule__VisibilityAssignment_0");
					put(grammarAccess.getParameterRuleAccess().getDirectionAssignment_1(), "rule__ParameterRule__DirectionAssignment_1");
					put(grammarAccess.getParameterRuleAccess().getNameAssignment_2(), "rule__ParameterRule__NameAssignment_2");
					put(grammarAccess.getParameterRuleAccess().getTypeAssignment_4_0(), "rule__ParameterRule__TypeAssignment_4_0");
					put(grammarAccess.getParameterRuleAccess().getMultiplicityAssignment_5(), "rule__ParameterRule__MultiplicityAssignment_5");
					put(grammarAccess.getParameterRuleAccess().getModifiersAssignment_6(), "rule__ParameterRule__ModifiersAssignment_6");
					put(grammarAccess.getParameterRuleAccess().getEffectAssignment_7(), "rule__ParameterRule__EffectAssignment_7");
					put(grammarAccess.getModifiersRuleAccess().getValuesAssignment_1(), "rule__ModifiersRule__ValuesAssignment_1");
					put(grammarAccess.getModifiersRuleAccess().getValuesAssignment_2_1(), "rule__ModifiersRule__ValuesAssignment_2_1");
					put(grammarAccess.getModifierSpecificationAccess().getValueAssignment(), "rule__ModifierSpecification__ValueAssignment");
					put(grammarAccess.getEffectRuleAccess().getEffectKindAssignment_2(), "rule__EffectRule__EffectKindAssignment_2");
					put(grammarAccess.getQualifiedNameAccess().getPathAssignment_0(), "rule__QualifiedName__PathAssignment_0");
					put(grammarAccess.getQualifiedNameAccess().getRemainingAssignment_2(), "rule__QualifiedName__RemainingAssignment_2");
					put(grammarAccess.getTypeRuleAccess().getPathAssignment_0(), "rule__TypeRule__PathAssignment_0");
					put(grammarAccess.getTypeRuleAccess().getTypeAssignment_1(), "rule__TypeRule__TypeAssignment_1");
					put(grammarAccess.getMultiplicityRuleAccess().getBoundsAssignment_1(), "rule__MultiplicityRule__BoundsAssignment_1");
					put(grammarAccess.getMultiplicityRuleAccess().getBoundsAssignment_2_1(), "rule__MultiplicityRule__BoundsAssignment_2_1");
					put(grammarAccess.getBoundSpecificationAccess().getValueAssignment(), "rule__BoundSpecification__ValueAssignment");
				}
			};
		}
		return nameMappings.get(element);
	}
	
	@Override
	protected Collection<FollowElement> getFollowElements(AbstractInternalContentAssistParser parser) {
		try {
			org.eclipse.papyrus.uml.textedit.parameter.xtext.ui.contentassist.antlr.internal.InternalUmlParameterParser typedParser = (org.eclipse.papyrus.uml.textedit.parameter.xtext.ui.contentassist.antlr.internal.InternalUmlParameterParser) parser;
			typedParser.entryRuleParameterRule();
			return typedParser.getFollowElements();
		} catch(RecognitionException ex) {
			throw new RuntimeException(ex);
		}		
	}
	
	@Override
	protected String[] getInitialHiddenTokens() {
		return new String[] { "RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT" };
	}
	
	public UmlParameterGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}
	
	public void setGrammarAccess(UmlParameterGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
}
