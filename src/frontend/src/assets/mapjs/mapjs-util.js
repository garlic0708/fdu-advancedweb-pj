const mapJS = require('mindmup-mapjs'),
  jQuery = require('jquery'),
  themeProvider = require('./theme'),
  ThemeProcessor = require('mindmup-mapjs-layout').ThemeProcessor,
  testMap = require('./example-map.json'),
  content = require('mindmup-mapjs-model').content;

export function init(container) {
  const mapModel = new mapJS.MapModel(mapJS.DOMRender.layoutCalculator, []);
  const jqContainer = jQuery(container);
  jqContainer.domMapWidget(console, mapModel);
  console.log(testMap);
  mapModel.setIdea(content(testMap));
  const stylesheet = new ThemeProcessor().process(themeProvider.default).css;
  jqContainer.append(`
<style>/*noinspection CssUnusedSymbol*/${stylesheet}.mapjs-link-hit {visibility: hidden;}
</style>`);
  return mapModel;
}
