import { MindmapHolderRoutingModule } from './mindmap-holder-routing.module';

describe('MindmapHolderRoutingModule', () => {
  let mindmapHolderRoutingModule: MindmapHolderRoutingModule;

  beforeEach(() => {
    mindmapHolderRoutingModule = new MindmapHolderRoutingModule();
  });

  it('should create an instance', () => {
    expect(mindmapHolderRoutingModule).toBeTruthy();
  });
});
