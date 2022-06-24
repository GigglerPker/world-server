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
package com.rs.game.content.skills.construction;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.content.dialogue.Conversation;
import com.rs.game.content.dialogue.HeadE;
import com.rs.game.model.entity.npc.NPC;
import com.rs.game.model.entity.player.Player;

public class ItemOnServantD extends Conversation {

	public ItemOnServantD(Player player, NPC servant, int item, boolean isSawmill) {
		super(player);
		boolean proceed = false;
		for (int bankable : HouseConstants.BANKABLE_ITEMS) {
			if (item == bankable) {
				proceed = true;
				break;
			}
		}
		ItemDefinitions definition = ItemDefinitions.getDefs(item);
		final int[] plank = SawmillOperator.getPlankForLog(item);
		if (plank != null || definition.isNoted())
			proceed = true;
		if (!proceed)
			return;
		int paymentStage = player.getHouse().getPaymentStage();
		if (paymentStage == 1) {
			addNPC(servant.getId(), HeadE.CALM_TALK, "Excuse me, but before I can continue working you must pay my fee.");
			return;
		}
		String name = definition.getName().toLowerCase();

		if (definition.isNoted()) {
			addOptions("Un-cert this item?", ops -> {
				ops.add("Un-cert " + name + ".", () -> setFetchAttributes(2, item, "How many would you like to un-note?"));
				ops.add("Fetch another " + name + ".", () -> setFetchAttributes(0, item, "How many would you like to retrieve?"));
				ops.add("Bank", () -> setFetchAttributes(3, item, "How many would you like to bank?"));
				ops.add("Cancel");
			});
		} else if (isSawmill && plank != null) {
			addOptions("Take this to the sawmill?", ops -> {
				ops.add("Take it to the sawmill.", () -> setFetchAttributes(1, item, "How many would you like to create?"));
				ops.add("Bank", () -> setFetchAttributes(3, item, "How many would you like to bank?"));
				ops.add("Cancel");
			});
		} else {
			addOptions("Take this item to the bank?", ops -> {
				ops.add("Fetch another " + name + ".", () -> setFetchAttributes(0, item, "How many would you like to retrieve?"));
				ops.add("Bank", () -> setFetchAttributes(3, item, "How many would you like to bank?"));
				ops.add("Cancel");
			});
		}
	}

	private void setFetchAttributes(int type, int item, String title) {
		player.sendInputInteger(title, amount -> {
			if (!player.getHouse().isLoaded() || !player.getHouse().getPlayers().contains(player))
				return;
			player.getHouse().getServantInstance().requestType(item, amount, (byte) type);
		});
	}
}
