import { MindmapBodyModule } from './mindmap-body.module';

describe('MindmapBodyModule', () => {
  let mindmapBodyModule: MindmapBodyModule;

  beforeEach(() => {
    mindmapBodyModule = new MindmapBodyModule();
  });

  it('should create an instance', () => {
    expect(mindmapBodyModule).toBeTruthy();
  });
});
