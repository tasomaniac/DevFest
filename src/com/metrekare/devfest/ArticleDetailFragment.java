/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.metrekare.devfest;


import java.util.Locale;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.MenuItem;
import com.netmera.mobile.NetmeraCallback;
import com.netmera.mobile.NetmeraContent;
import com.netmera.mobile.NetmeraException;
import com.netmera.mobile.NetmeraService;
import com.squareup.picasso.Picasso;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

/**
 * A fragment representing a single Article detail screen. This fragment is
 * either contained in a {@link ArticleListActivity} in two-pane mode (on
 * tablets) or a {@link ArticleDetailActivity} on handsets.
 */
public class ArticleDetailFragment extends SherlockFragment {
	
	public static String CONTENT = "Bacon ipsum dolor sit amet <a href='foo.html'>frankfurter tenderloin</a> beef ribs pig turducken,"
            + " tail jowl cow bresaola pork shoulder pastrami short ribs drumstick"
            + " strip steak.<br>"
            + "<br>"
            + "Beef ribs kielbasa sirloin pork loin chicken pork chop"
            + " rump andouille tail. Beef ribs corned beef sausage, doner shoulder"
            + " capicola pork pastrami jowl chuck shankle. T-bone ribeye chicken"
            + " turducken drumstick rump prosciutto tri-tip pork belly sausage"
            + " shankle venison shoulder pastrami ball tip.<br>"
            + "<br>"
            + "Frankfurter ball tip pork belly shoulder short loin. Boudin"
            + " andouille ham hock tri-tip tail, capicola t-bone fatback kielbasa"
            + " venison cow drumstick ribeye biltong. Shoulder ribeye hamburger,"
            + " pork belly strip steak chuck spare ribs ham hock salami. Turkey"
            + " filet mignon t-bone, ribeye tail boudin jowl short loin andouille"
            + " spare ribs. Cow tri-tip ball tip chuck, leberkas venison meatball"
            + " pastrami salami short loin bresaola. Turducken sirloin turkey"
            + " ribeye bresaola jowl bacon meatloaf sausage.<br>"
            + "<br>"
            + "Brisket doner tail capicola. Ham swine biltong jowl ribeye jerky"
            + " tenderloin pork belly hamburger venison brisket. Capicola ground"
            + " round pancetta jowl, turducken pork belly doner venison spare"
            + " ribs boudin frankfurter. Cow swine ball tip jowl, hamburger salami"
            + " prosciutto biltong ribeye venison tail short loin chuck turkey"
            + ". Leberkas fatback tongue, shoulder prosciutto strip steak ground"
            + " round short ribs kielbasa short loin flank. Meatball drumstick"
            + " turkey pork loin. Cow spare ribs chuck, beef ribs tongue ham salami"
            + " swine drumstick capicola jowl sirloin pork bresaola.";
	
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    /**
     * The dummy content this fragment is presenting.
     */
    private NetmeraContent mItem;

    private View rootView;
    
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ArticleDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
//            mItem = new NetmeraContent("article");
//            mItem.setPath(getArguments().getString(
//                    ARG_ITEM_ID));
            
            NetmeraService service = new NetmeraService("article");
            service.setPath(getArguments().getString(
                    ARG_ITEM_ID));
            service.getInBackground(new NetmeraCallback<NetmeraContent>() {

				@Override
				public void onFail(NetmeraException exception) {
					Crouton.showText(getActivity(), exception.getMessage(), Style.ALERT);
				}

				@Override
				public void onSuccess(NetmeraContent result) {
					
					mItem = result;
					fill();
				}
            	
			});
//            mItem.
            		//DummyContent.ITEM_MAP.get(getArguments().getString(
//                    ARG_ITEM_ID));
        }

        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. Use NavUtils to allow users
                // to navigate up one level in the application structure. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back
                //
                NavUtils.navigateUpTo(getActivity(), new Intent(getActivity(),
                        ArticleListActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_article_detail,
                container, false);

        return rootView;
    }
    
    public void fill() {
    	// Show the dummy content as text in a TextView.
    	try {
	        if (mItem != null) {
	            ((TextView) rootView.findViewById(R.id.article_title))
	                    .setText(mItem.getString("title"));
	            ((TextView) rootView.findViewById(R.id.article_byline))
	                    .setText(Html.fromHtml(mItem.getString("article_date").toUpperCase(Locale.getDefault())
	                            + " BY <font color='"
	                            + getResources().getString(R.string.author_font_color) + "'>"
	                            + mItem.getString("author").toUpperCase(Locale.getDefault()) + "</a>"));
	            ((TextView) rootView.findViewById(R.id.article_byline))
	                    .setMovementMethod(new LinkMovementMethod());
	//            ((TextView) rootView.findViewById(R.id.article_date))
	//                    .setText(mItem.time);
	            ((TextView) rootView.findViewById(R.id.article_body))
	                    .setText(Html.fromHtml(CONTENT));
	            
	            Picasso.with(getActivity()).load(mItem.getString("imageUrl")).into((ImageView) rootView.findViewById(R.id.photo));
	            
	        }
    	} catch(NetmeraException e ){}
    }
}
