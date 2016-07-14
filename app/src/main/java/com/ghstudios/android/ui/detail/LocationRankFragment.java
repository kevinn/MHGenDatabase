package com.ghstudios.android.ui.detail;

import java.io.IOException;

import android.content.Context;
import android.content.Intent;
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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.monxalo.android.widget.SectionCursorAdapter;
import com.ghstudios.android.data.classes.Gathering;
import com.ghstudios.android.data.database.GatheringCursor;
import com.ghstudios.android.loader.GatheringListCursorLoader;
import com.ghstudios.android.mhgendatabase.R;
import com.ghstudios.android.ui.ClickListeners.ItemClickListener;

public class LocationRankFragment extends ListFragment implements
		LoaderCallbacks<Cursor> {

	private static final String ARG_LOCATION = "LOCATION_ID";
	private static final String ARG_RANK = "RANK_ID";

	public static LocationRankFragment newInstance(Long location, String rank) {
		Bundle args = new Bundle();
		args.putLong(ARG_LOCATION, location);
		args.putString(ARG_RANK, rank);
		LocationRankFragment f = new LocationRankFragment();
		f.setArguments(args);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		int loaderId = 0;
		String mRank = getArguments().getString(ARG_RANK);
		
		if (mRank.equals("LR")) {
			loaderId = R.id.location_rank_fragment_low;
		}
		else if (mRank.equals("HR")) {
			loaderId = R.id.location_rank_fragment_high;
		}
		else if (mRank.equals("G")) {
			loaderId = R.id.location_rank_fragment_g;
		}
		
		// Initialize the loader to load the list of runs
		getLoaderManager().initLoader(loaderId, getArguments(), this);
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
		Long mLocation = null;
		String mRank = null;
		if (args != null) {
			mLocation = args.getLong(ARG_LOCATION);
			mRank = args.getString(ARG_RANK);
		}

		return new GatheringListCursorLoader(getActivity(), 
				GatheringListCursorLoader.FROM_LOCATION,
				mLocation, mRank);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		// Create an adapter to point at this cursor
		GatheringListCursorAdapter adapter = new GatheringListCursorAdapter(
				getActivity(), (GatheringCursor) cursor);
		setListAdapter(adapter);

	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// Stop using the cursor (via the adapter)
		setListAdapter(null);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// The id argument will be the Monster ID; CursorAdapter gives us this
		// for free
		Intent i = new Intent(getActivity(), ItemDetailActivity.class);
		i.putExtra(ItemDetailActivity.EXTRA_ITEM_ID, (long) v.getTag());
		startActivity(i);
	}

	private static class GatheringListCursorAdapter extends SectionCursorAdapter {

		private GatheringCursor mGatheringCursor;

		public GatheringListCursorAdapter(Context context,
				GatheringCursor cursor) {
			super(context, cursor, R.layout.listview_generic_header, "area");
			mGatheringCursor = cursor;
		}

		@Override
		protected String getCustomGroup(Cursor c) {
			Gathering g = ((GatheringCursor)c).getGathering();
			return g.getArea() +" "
					+(g.isFixed()?"Fixed ":"Random ")
					+g.getSite() + " "
					+g.getGroup()
					+(g.isFixed()?"":(g.isRare()?" [Rare]":" [Normal]"));
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			// Use a layout inflater to get a row view
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			return inflater.inflate(R.layout.fragment_location_rank_listitem,
					parent, false);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			// Get the skill for the current row
			Gathering gathering = mGatheringCursor.getGathering();

			// Set up the text view
			RelativeLayout itemLayout = (RelativeLayout) view
					.findViewById(R.id.listitem);
			ImageView itemImageView = (ImageView) view
					.findViewById(R.id.item_image);

			TextView itemTextView = (TextView) view.findViewById(R.id.item);
            TextView rateTextView = (TextView) view.findViewById(R.id.percentage);
			TextView amountTextView = (TextView)view.findViewById(R.id.amount);

			String cellItemText = gathering.getItem().getName();
            long rate = (long) gathering.getRate();

			itemTextView.setText(cellItemText);
            rateTextView.setText(Long.toString(rate) + "%");
			amountTextView.setText("x" + gathering.getQuantity());

			Drawable i = null;
			String cellImage = "icons_items/"
					+ gathering.getItem().getFileLocation();

			try {
				i = Drawable.createFromStream(
						context.getAssets().open(cellImage), null);
			} catch (IOException e) {
				e.printStackTrace();
			}

			itemImageView.setImageDrawable(i);

			itemLayout.setTag(gathering.getItem().getId());
            itemLayout.setOnClickListener(new ItemClickListener(context,
                    gathering.getItem().getId()));

		}

	}

}
