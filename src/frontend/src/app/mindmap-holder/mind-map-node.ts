export interface MindMapNode {
  id: number,
  internalId: number,
  color?: string,
  name: string,
  childNodes: MindMapNode[],
}
