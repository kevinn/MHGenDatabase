package com.ghstudios.android.ui.detail;

import java.io.IOException;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ghstudios.android.data.database.S;
import com.ghstudios.android.ui.ClickListeners.MaterialClickListener;
import com.github.monxalo.android.widget.SectionCursorAdapter;
import com.ghstudios.android.data.classes.Component;
import com.ghstudios.android.data.database.ComponentCursor;
import com.ghstudios.android.loader.ComponentListCursorLoader;
import com.ghstudios.android.mhgendatabase.R;
import com.ghstudios.android.ui.ClickListeners.ArmorClickListener;
import com.ghstudios.android.ui.ClickListeners.DecorationClickListener;
import com.ghstudios.android.ui.ClickListeners.ItemClickListener;
import com.ghstudios.android.ui.ClickListeners.WeaponClickListener;

public class ComponentListFragment extends ListFragment implements
		LoaderCallbacks<Cursor> {

	private static final String ARG_ITEM_ID = "COMPONENT_ID";

	public static ComponentListFragment newInstance(long id) {
		Bundle args = new Bundle();
		args.putLong(ARG_ITEM_ID, id);
		ComponentListFragment f = new ComponentListFragment();
		f.setArguments(args);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Initialize the loader to load the list of runs
		getLoaderManager().initLoader(R.id.component_list_fragment, getArguments(), this);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_generic_list, null);
		return v;
	}


	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		// You only ever load the runs, so assume this is the case
		long mId = -1;
		if (args != null) {
			mId = args.getLong(ARG_ITEM_ID);
		}
		return new ComponentListCursorLoader(getActivity(), 
				ComponentListCursorLoader.FROM_CREATED, mId);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		// Create an adapter to point at this cursor
		ComponentListCursorAdapter adapter = new ComponentListCursorAdapter(
				getActivity(), (ComponentCursor) cursor);
		setListAdapter(adapter);

	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// Stop using the cursor (via the adapter)
		setListAdapter(null);
	}

	private static class ComponentListCursorAdapter extends SectionCursorAdapter {

		private ComponentCursor mComponentCursor;

		public ComponentListCursorAdapter(Context context, ComponentCursor cursor) {
			super(context, cursor, R.layout.listview_generic_header,cursor.getColumnIndex(S.COLUMN_COMPONENTS_TYPE));
			mComponentCursor = cursor;
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			// Use a layout inflater to get a row view
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			return inflater.inflate(R.layout.fragment_component_listitem,
					parent, false);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			// Get the skill for the current row
			Component component = mComponentCursor.getComponent();

			// Set up the text view
			LinearLayout itemLayout = (LinearLayout) view
					.findViewById(R.id.listitem);
			ImageView itemImageView = (ImageView) view.findViewById(R.id.item_image);
			TextView itemTextView = (TextView) view.findViewById(R.id.item);
			TextView amtTextView = (TextView) view.findViewById(R.id.amt);
			
			String nameText = component.getComponent().getName();
			String amtText = "" + component.getQuantity();
			
			itemTextView.setText(nameText);
			amtTextView.setText(amtText);
			
			Drawable i = null;
            String cellImage;

            String sub_type = component.getComponent().getSubType();

            switch(sub_type){
                case "Head":
                    cellImage = "icons_armor/icons_head/head" + component.getComponent().getRarity() + ".png";
                    break;
                case "Body":
                    cellImage = "icons_armor/icons_body/body" + component.getComponent().getRarity() + ".png";
                    break;
                case "Arms":
                    cellImage = "icons_armor/icons_arms/arms" + component.getComponent().getRarity() + ".png";
                    break;
                case "Waist":
                    cellImage = "icons_armor/icons_waist/waist" + component.getComponent().getRarity() + ".png";
                    break;
                case "Legs":
                    cellImage = "icons_armor/icons_legs/legs" + component.getComponent().getRarity() + ".png";
                    break;
                case "Great Sword":
                    cellImage = "icons_weapons/icons_great_sword/great_sword" + component.getComponent().getRarity() + ".png";
                    break;
                case "Long Sword":
                    cellImage = "icons_weapons/icons_long_sword/long_sword" + component.getComponent().getRarity() + ".png";
                    break;
                case "Sword and Shield":
                    cellImage = "icons_weapons/icons_sword_and_shield/sword_and_shield" + component.getComponent().getRarity() + ".png";
                    break;
                case "Dual Blades":
                    cellImage = "icons_weapons/icons_dual_blades/dual_blades" + component.getComponent().getRarity() + ".png";
                    break;
                case "Hammer":
                    cellImage = "icons_weapons/icons_hammer/hammer" + component.getComponent().getRarity() + ".png";
                    break;
                case "Hunting Horn":
                    cellImage = "icons_weapons/icons_hunting_horn/hunting_horn" + component.getComponent().getRarity() + ".png";
                    break;
                case "Lance":
                    cellImage = "icons_weapons/icons_lance/lance" + component.getComponent().getRarity() + ".png";
                    break;
                case "Gunlance":
                    cellImage = "icons_weapons/icons_gunlance/gunlance" + component.getComponent().getRarity() + ".png";
                    break;
                case "Switch Axe":
                    cellImage = "icons_weapons/icons_switch_axe/switch_axe" + component.getComponent().getRarity() + ".png";
                    break;
                case "Charge Blade":
                    cellImage = "icons_weapons/icons_charge_blade/charge_blade" + component.getComponent().getRarity() + ".png";
                    break;
                case "Insect Glaive":
                    cellImage = "icons_weapons/icons_insect_glaive/insect_glaive" + component.getComponent().getRarity() + ".png";
                    break;
                case "Light Bowgun":
                    cellImage = "icons_weapons/icons_light_bowgun/light_bowgun" + component.getComponent().getRarity() + ".png";
                    break;
                case "Heavy Bowgun":
                    cellImage = "icons_weapons/icons_heavy_bowgun/heavy_bowgun" + component.getComponent().getRarity() + ".png";
                    break;
                case "Bow":
                    cellImage = "icons_weapons/icons_bow/bow" + component.getComponent().getRarity() + ".png";
                    break;
                default:
                    cellImage = "icons_items/" + component.getComponent().getFileLocation();
            }
			try {
				i = Drawable.createFromStream(
						context.getAssets().open(cellImage), null);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			itemImageView.setImageDrawable(i);

            long id = component.getComponent().getId();
			itemLayout.setTag(id);

            String itemtype = component.getComponent().getType();
            switch(itemtype){
                case "Weapon":
                    itemLayout.setOnClickListener(new WeaponClickListener(context, id));
                    break;
                case "Armor":
                    itemLayout.setOnClickListener(new ArmorClickListener(context, id));
                    break;
                case "Decoration":
                    itemLayout.setOnClickListener(new DecorationClickListener(context, id));
                    break;
                case "Materials":
                    itemLayout.setOnClickListener(new MaterialClickListener(context,id));
                    break;
                default:
                    itemLayout.setOnClickListener(new ItemClickListener(context, id));
                    break;
            }
		}
	}

}
