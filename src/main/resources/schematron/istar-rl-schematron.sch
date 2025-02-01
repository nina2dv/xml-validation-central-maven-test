<?xml version="1.0" encoding="UTF-8"?>
<sch:schema
    xmlns:sch="http://purl.oclc.org/dsdl/schematron"
    xmlns:istar="https://example.org/istar-rl"
    queryBinding="xslt2">

  <!-- Declare the 'istar' prefix here -->
  <sch:ns prefix="istar" uri="https://example.org/istar-rl"/>

  <!-- Pattern to Check Descriptions -->
  <sch:pattern id="CheckDescriptions">
    <!-- Match any istar:* element with a @name -->
    <sch:rule context="istar:*[@name]">
      <!-- Missing description => report (warning) -->
      <sch:report test="not(@description)" flag="warning">
        <sch:text>
          Element &lt;<sch:value-of select="name()"/>&gt; with @name="<sch:value-of select="@name"/>" has no @description.
        </sch:text>
      </sch:report>
    </sch:rule>
  </sch:pattern>

  <!-- Pattern to Check Root Goals Episode Length -->
  <sch:pattern id="CheckRootGoalsEpisodeLength">
    <sch:rule context="istar:goal[@root='true']">
      <!-- Missing @episodeLength => warning -->
      <sch:report test="not(@episodeLength)" flag="warning">
        <sch:text>
          Root goal "<sch:value-of select="@name"/>" has no @episodeLength; default = 1 assumed.
        </sch:text>
      </sch:report>

      <!-- Invalid @episodeLength => error -->
      <sch:assert test="@episodeLength castable as xs:integer and @episodeLength &gt; 0" flag="error">
        <sch:text>
          Root goal "<sch:value-of select="@name"/>" has an invalid @episodeLength. It must be a positive integer.
        </sch:text>
      </sch:assert>
    </sch:rule>
  </sch:pattern>

  <!-- Pattern to Check Leaf Goals Refinement -->
  <sch:pattern id="CheckLeafGoalsRefinement">
    <sch:rule context="istar:goal">
      <!-- No <refinement> => warning -->
      <sch:report test="not(istar:refinement)" flag="warning">
        <sch:text>
          Goal "<sch:value-of select="@name"/>" has no &lt;refinement&gt; (leaf-level goal).
        </sch:text>
      </sch:report>
    </sch:rule>
  </sch:pattern>

  <!-- Pattern to Check Refinement Children Count -->
  <sch:pattern id="CheckRefinementChildrenCount">
    <sch:rule context="istar:refinement[@type='AND']">
      <sch:assert test="count(istar:childGoal | istar:childTask) &gt;= 2" flag="error">
        <sch:text>
          AND-refinement must have at least 2 children.
        </sch:text>
      </sch:assert>
    </sch:rule>
    <sch:rule context="istar:refinement[@type='OR']">
      <sch:assert test="count(istar:childGoal | istar:childTask) &gt;= 1" flag="error">
        <sch:text>
          OR-refinement must have at least 1 child.
        </sch:text>
      </sch:assert>
    </sch:rule>
  </sch:pattern>

  <!-- Pattern to Check Task Reference Count -->
  <sch:pattern id="CheckTaskReferenceCount">
    <sch:rule context="istar:task">
      <sch:let name="thisName" value="@name"/>
      <!-- Count how many istar:childTask elements refer to this task, anywhere under the same actor -->
      <sch:let name="refCount" value="count(ancestor::istar:actor//istar:childTask[@ref = $thisName])"/>
      <!-- Not exactly once => warning -->
      <sch:report test="$refCount != 1" flag="warning">
        <sch:text>
          Task "<sch:value-of select="@name"/>" is referenced <sch:value-of select="$refCount"/> time(s); must be exactly once.
        </sch:text>
      </sch:report>
    </sch:rule>
  </sch:pattern>

  <!-- Pattern to Check Child References Exist -->
  <sch:pattern id="CheckChildRefsExist">
    <sch:rule context="istar:childGoal">
      <sch:assert test="@ref = ancestor::istar:actor//istar:goal/@name" flag="error">
        <sch:text>
          childGoal ref="<sch:value-of select="@ref"/>" does not match any &lt;goal name="..."&gt;.
        </sch:text>
      </sch:assert>
    </sch:rule>
    <sch:rule context="istar:childTask">
      <sch:assert test="@ref = ancestor::istar:actor//istar:task/@name" flag="error">
        <sch:text>
          childTask ref="<sch:value-of select="@ref"/>" does not match any &lt;task name="..."&gt;.
        </sch:text>
      </sch:assert>
    </sch:rule>
  </sch:pattern>

  <!-- Pattern to Check Effects Probability -->
  <sch:pattern id="CheckEffectsProbability">
    <sch:rule context="istar:effect">
      <!-- Probability must be 0..1 => error -->
      <sch:assert test="@probability castable as xs:decimal and @probability &gt;= 0 and @probability &lt;= 1" flag="error">
        <sch:text>
          Effect "<sch:value-of select="@name"/>" has an invalid @probability attribute. It must be a decimal between 0 and 1.
        </sch:text>
      </sch:assert>

      <!-- Missing @satisfying => warning -->
      <sch:report test="not(@satisfying)" flag="warning">
        <sch:text>
          Effect "<sch:value-of select="@name"/>" has no @satisfying. Default is "true."
        </sch:text>
      </sch:report>

      <!-- @satisfying must be 'true' or 'false' => error -->
      <sch:assert test="not(@satisfying) or @satisfying = 'true' or @satisfying = 'false'" flag="error">
        <sch:text>
          Effect "<sch:value-of select="@name"/>" has an invalid @satisfying attribute. It must be "true" or "false".
        </sch:text>
      </sch:assert>
    </sch:rule>
  </sch:pattern>

<!-- Pattern to Check Sum of Probabilities in Each EffectGroup -->
<sch:pattern id="CheckEffectGroupProbabilitySum">
  <sch:rule context="istar:effectGroup">
    <!-- Assert that the sum of @probability equals 1.0 within a small tolerance -->
    <sch:assert test="abs(xs:decimal(sum(istar:effect/@probability)) - 1.0) le 0.001" flag="error">
      <sch:text>
        The sum of @probability in &lt;effectGroup&gt; should be 1.0, but it is <sch:value-of select="format-number(sum(istar:effect/@probability), '0.000')"/>.
      </sch:text>
    </sch:assert>
  </sch:rule>
</sch:pattern>


<!-- Pattern to Check TurnsTrue and TurnsFalse in Predicates -->
<sch:pattern id="CheckTurnsTrueInPredicates">
  <sch:rule context="istar:turnsTrue | istar:turnsFalse">
    <sch:assert test="normalize-space(.) = ancestor::istar:actor//istar:predicates/istar:predicate/normalize-space()"
               flag="error">
      <sch:text>
        <sch:value-of select="name()"/>="
        <sch:value-of select="."/>" not found among &lt;predicate&gt; definitions.
      </sch:text>
    </sch:assert>
  </sch:rule>
</sch:pattern>


  <!-- Pattern to Check Pre/NPR References -->
  <sch:pattern id="CheckPreNprReferences">
    <sch:rule context="istar:pre | istar:npr">
      <sch:let name="ref" value="normalize-space(.)"/>
      <sch:assert
        test="
          $ref = ancestor::istar:actor//istar:preBox/@name
          or $ref = ancestor::istar:actor//istar:goal/@name
          or $ref = ancestor::istar:actor//istar:task/@name
          or $ref = ancestor::istar:actor//istar:effectGroup/istar:effect/@name
        "
        flag="error">
        <sch:text>
          reference "<sch:value-of select="$ref"/>" not found as preBox, goal, task, or effect name.
        </sch:text>
      </sch:assert>
    </sch:rule>
  </sch:pattern>

  <!-- Pattern to Check Atoms in Boolean Expressions -->
 <sch:pattern id="CheckBooleanExpressionAtoms">
   <sch:rule context="istar:preBox">
     <sch:let name="actor" value="ancestor::istar:actor"/>
     <!-- Normalize whitespace and tokenize based on whitespace -->
     <sch:let name="atoms" value="tokenize(normalize-space(.), '\s+')"/>
     <sch:let name="predicateNames" value="$actor//istar:predicates/istar:predicate"/>
     <sch:let name="qualityNames" value="$actor//istar:qualities/istar:quality/@name"/>
     <sch:let name="undefinedAtoms" value="
       $atoms[
         not(. = $predicateNames)
         and not(. = $qualityNames)
         and not(. = 'and')
         and not(. = 'or')
         and not(. = 'not')
         and not(. = 'true')
         and not(. = 'false')
       ]
     "/>
     <!-- If any undefined atoms, that's an error -->
     <sch:assert test="count($undefinedAtoms) = 0" flag="error">
       <sch:text>
         The following atoms in the expression are not declared as predicates or qualities:
         <sch:value-of select="string-join($undefinedAtoms, ', ')"/>.
       </sch:text>
     </sch:assert>
   </sch:rule>
 </sch:pattern>

  <!-- Pattern to Check Atoms in Numeric Expressions -->
 <sch:pattern id="CheckNumericExpressionAtoms">
   <sch:rule context="istar:quality/istar:value">
     <sch:let name="actor" value="ancestor::istar:actor"/>
     <!-- Normalize whitespace and tokenize based on whitespace -->
     <sch:let name="atoms" value="tokenize(normalize-space(.), '\s+')"/>
     <sch:let name="predicateNames" value="$actor//istar:predicates/istar:predicate"/>
     <sch:let name="qualityNames" value="$actor//istar:qualities/istar:quality/@name"/>
     <sch:let name="undefinedAtoms" value="
       $atoms[
         not(. = $predicateNames)
         and not(. = $qualityNames)
         and not(. = 'previous')
         and not(. castable as xs:decimal)
         and not(. castable as xs:integer)
         and not(. = '+')
         and not(. = '-')
         and not(. = '*')
         and not(. = '/')
         and not(. = '(')
         and not(. = ')')
       ]
     "/>
     <!-- If any undefined atoms, that's an error -->
     <sch:assert test="count($undefinedAtoms) = 0" flag="error">
       <sch:text>
         The following atoms in the numeric expression are not declared as predicates or qualities:
         <sch:value-of select="string-join($undefinedAtoms, ', ')"/>.
       </sch:text>
     </sch:assert>
   </sch:rule>
 </sch:pattern>


  <!-- Pattern to Check Root Quality -->
  <sch:pattern id="CheckRootQuality">
    <sch:rule context="istar:quality[@root='true']">
      <sch:assert test="count(ancestor::istar:actor/istar:qualities/istar:quality[@root='true']) = 1" flag="error">
        <sch:text>
          There must be exactly one root quality per actor.
        </sch:text>
      </sch:assert>
    </sch:rule>
  </sch:pattern>

</sch:schema>
