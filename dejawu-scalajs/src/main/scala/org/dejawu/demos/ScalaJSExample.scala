package org.dejawu.demos

import scala.scalajs.js
import js.annotation.JSExport
import js.Dynamic.{ global => g }
 
import scalatags.Text.all._
import org.dejawu.DojoText.all._
 
 
object ScalaJSExample extends js.JSApp {
 
  def main() = {
    g.document.body.removeChild( g.document.getElementById("loading") )
    g.document.getElementById("container").innerHTML = render().toString()
  }
 
  private def render() =
  dijit.layout.BorderContainer(`data-dojo-props` := "gutters:true, liveSplitters:false", id := "borderContainer", width := "100%", height := "100%")(
    dijit.layout.ContentPane(`data-dojo-props` := "region:'top', splitter:false")("dejawu Elements"),
    dijit.layout.AccordionContainer(style := "width: 300px;", `data-dojo-props` := "minSize:20, region:'leading', splitter:true", id := "leftAccordion")(
      dijit.layout.AccordionPane(selected := "true", title := "Tree")(
        dojo.store.Memory(`data-dojo-id` := "myStore")(
          script(`type` := "dojo/method")("""
            |this.setData([
            |  { id: 'world', name:'The earth', type:'planet', population: '6 billion'},
            |  { id: 'AF', name:'Africa', type:'continent', population:'900 million', area: '30,221,532 sq km',
            |          timezone: '-1 UTC to +4 UTC', parent: 'world'},
            |      { id: 'EG', name:'Egypt', type:'country', parent: 'AF' },
            |      { id: 'KE', name:'Kenya', type:'country', parent: 'AF' },
            |          { id: 'Nairobi', name:'Nairobi', type:'city', parent: 'KE' },
            |          { id: 'Mombasa', name:'Mombasa', type:'city', parent: 'KE' },
            |      { id: 'SD', name:'Sudan', type:'country', parent: 'AF' },
            |          { id: 'Khartoum', name:'Khartoum', type:'city', parent: 'SD' },
            |  { id: 'AS', name:'Asia', type:'continent', parent: 'world' },
            |      { id: 'CN', name:'China', type:'country', parent: 'AS' },
            |      { id: 'IN', name:'India', type:'country', parent: 'AS' },
            |      { id: 'RU', name:'Russia', type:'country', parent: 'AS' },
            |      { id: 'MN', name:'Mongolia', type:'country', parent: 'AS' },
            |  { id: 'OC', name:'Oceania', type:'continent', population:'21 million', parent: 'world'},
            |  { id: 'EU', name:'Europe', type:'continent', parent: 'world' },
            |      { id: 'DE', name:'Germany', type:'country', parent: 'EU' },
            |      { id: 'FR', name:'France', type:'country', parent: 'EU' },
            |      { id: 'ES', name:'Spain', type:'country', parent: 'EU' },
            |      { id: 'IT', name:'Italy', type:'country', parent: 'EU' },
            |  { id: 'NA', name:'North America', type:'continent', parent: 'world' },
            |  { id: 'SA', name:'South America', type:'continent', parent: 'world' }
            |]);""".stripMargin),
          script(`data-dojo-args` := "object", `data-dojo-event` := "getChildren", `type` := "dojo/method")("""
                     // Supply a getChildren() method to store for the data model where
                     // children objects point to their parent (aka relational model)
                     return this.query({parent: object.id});
                """)),
        dijit.tree.ObjectStoreModel(`data-dojo-props` := "store: myStore, query: {id: 'world'}", `data-dojo-id` := "myModel"),
        dijit.Tree(`data-dojo-props` := "model: myModel", id := "myTree")),
      dijit.layout.AccordionPane(title := "Grid")("""
Work in progress
        """),
      dijit.layout.AccordionPane(title := "Even more fancy")(),
      dijit.layout.AccordionPane(title := "Last, but not least")()),
    dijit.layout.TabContainer(`data-dojo-props` := "region:'center', tabStrip:true")(
      dijit.layout.ContentPane(selected := "true", title := "Common widgets")(
        div()("""
                dijit/form/Button
                """,
          dijit.form.Button(`type` := "button")("""Click me too!
                    """,
            script(`data-dojo-args` := "evt", `data-dojo-event` := "click", `type` := "dojo/on")("""
                        require([&quot;dojo/dom&quot;], function(dom){
                            dom.byId(&quot;result2&quot;).innerHTML += &quot;Thank you! &quot;;
                        });
                    """)),
          dijit.form.Button(`data-dojo-props` := "iconClass:'dijitEditorIcon dijitEditorIconCut', showLabel: false", `type` := "button")("""clear
                    """,
            script(`data-dojo-args` := "evt", `data-dojo-event` := "click", `type` := "dojo/on")("""
                        require([&quot;dojo/dom&quot;], function(dom){
                            dom.byId(&quot;result2&quot;).innerHTML = &quot;&quot;;
                        });
                    """)),
          dijit.form.ToggleButton(`data-dojo-props` := "iconClass:'dijitCheckBoxIcon', checked: true", `type` := "submit")("""
                    Toggle me
                """),
          div(id := "result2")),
        div()("""
                dijit/form/Select (TODO: store)
                """,
          dijit.form.Select(name := "select1")(
            option(value := "TN")("Tennessee"),
            option(value := "VA", selected := "selected")("Virginia"),
            option(value := "WA")("Washington"),
            option(value := "FL")("Florida"),
            option(value := "CA")("California"))),
        div()("""
                dijit/form/MultiSelect
                """,
          dijit.form.MultiSelect(name := "fruit", id := "fruit", size := "4")(
            option(value := "AP")("Apples"),
            option(value := "OR")("Oranges"),
            option(value := "PE", selected := "selected")("Pears"))),
        div()("""
                dijit/form/ComboBox
                """,
          dojo.store.Memory(`data-dojo-id` := "stateStore", `data-dojo-props` := """
            |data: [
            |  { id: 'EG', name:'Egypt'    },
            |  { id: 'KE', name:'Kenya'    },
            |  { id: 'SD', name:'Sudan'    },
            |  { id: 'CN', name:'China'    },
            |  { id: 'IN', name:'India'    },
            |  { id: 'RU', name:'Russia'   },
            |  { id: 'MN', name:'Mongolia' },
            |  { id: 'DE', name:'Germany'  },
            |  { id: 'FR', name:'France'   },
            |  { id: 'ES', name:'Spain'    },
            |  { id: 'IT', name:'Italy'    } ]""".stripMargin),
          dijit.form.ComboBox(value := "Egypt", name := "state", `data-dojo-props` := "store:stateStore, searchAttr:'name'", id := "stateInput", `type` := "text"))),
      dijit.layout.ContentPane(title := "Forms")("""
            To be done (forms)
        """),
      dijit.layout.ContentPane(title := "What else?", `data-dojo-props` := "closable:true")("""
            To be done
        """)))

}
