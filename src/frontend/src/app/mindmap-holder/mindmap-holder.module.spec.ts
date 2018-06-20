import { MindmapHolderModule } from './mindmap-holder.module';

describe('MindmapHolderModule', () => {
  let mindmapHolderModule: MindmapHolderModule;

  beforeEach(() => {
    mindmapHolderModule = new MindmapHolderModule();
  });

  it('should create an instance', () => {
    expect(mindmapHolderModule).toBeTruthy();
  });
});
