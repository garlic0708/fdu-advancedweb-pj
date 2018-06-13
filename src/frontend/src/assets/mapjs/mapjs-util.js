const mapJS = require('mindmup-mapjs'),
  jQuery = require('jquery'),
  themeProvider = require('./theme'),
  ThemeProcessor = require('mindmup-mapjs-layout').ThemeProcessor,
  content = require('mindmup-mapjs-model').content,
  get = require('lodash/get');
export const testMap = require('./example-map.json');

export function init(container, map) {
  const parentNodeMap = new Map(), colorMap = new Map();
  const traverseNodes = function (obj, parent) {
    (Object.keys(obj.ideas || {})).forEach(key => {
      const item = obj.ideas[key];
      parentNodeMap.set(item.id, parent);
      const color = get(item, 'attr.style.background');
      if (color) colorMap.set(item.id, color);
      traverseNodes(item, item.id)
    })
  };
  traverseNodes(map, undefined);
  console.log(parentNodeMap, colorMap);

  const mapModel = new mapJS.MapModel(mapJS.DOMRender.layoutCalculator, []);
  const jqContainer = jQuery(container);
  jqContainer.domMapWidget(console, mapModel);
  mapModel.setIdea(content(map));
  const stylesheet = new ThemeProcessor().process(themeProvider.default).css;
  jqContainer.append(`
<style>/*noinspection CssUnusedSymbol*/${stylesheet}.mapjs-link-hit {visibility: hidden;}
</style>`);

  mapModel.addEventListener('nodeAttrChanged', e => {
    const color = get(e, 'attr.style.background');
    if (color !== colorMap.get(e.id)) {
      colorMap.set(e.id, color);
      mapModel.dispatchEvent('colorChanged', e)
    }
    return false;
  });
  mapModel.addEventListener('nodeMoved', e => {
    const parentId = mapModel.getIdea().findParent(e.id).id;
    if (parentId !== parentNodeMap.get(e.id)) {
      parentNodeMap.set(e.id, parentId);
      mapModel.dispatchEvent('parentNodeChanged', e)
    }
    return false;
  });
  return mapModel;
}
