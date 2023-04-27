// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.
//
//  Copyright (C) 2021 Trenton Kress
//  This file is part of project: Darkan
//
package com.rs.engine.cutscene.actions;

import java.util.Map;

import com.rs.engine.cutscene.Cutscene;
import com.rs.game.model.entity.player.Player;
import com.rs.lib.game.Tile;

public class ConstructMapAction extends CutsceneAction {

	private Tile returnTile;
	private int baseChunkX, baseChunkY, widthChunks, heightChunks;

	public ConstructMapAction(Tile returnTile, int baseChunkX, int baseChunkY, int widthChunks, int heightChunks) {
		super(null, 1);
		this.returnTile = returnTile;
		this.baseChunkX = baseChunkX;
		this.baseChunkY = baseChunkY;
		this.widthChunks = widthChunks;
		this.heightChunks = heightChunks;
	}

	@Override
	public void process(Player player, Map<String, Object> objects) {
		Cutscene scene = (Cutscene) objects.get("cutscene");
		scene.constructArea(returnTile, baseChunkX, baseChunkY, widthChunks, heightChunks);
	}
}
