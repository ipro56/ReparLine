package com.droidstore.reparline.adapters;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.droidstore.reparline.R;
import com.droidstore.reparline.models.Post;
import com.droidstore.reparline.utils.Constant;
import com.droidstore.reparline.utils.ConversionUtils;
import com.droidstore.reparline.utils.ImageLoader;

public class AdapterListPost extends ArrayAdapter<Post> {

	// Atributos
	private Activity context;
	private List<Post> posts;

	// Views
	private TextView usernamePost, datePost, descriptionPost;

	private ImageView userImage;

	public AdapterListPost(Activity context, List<Post> posts)
			throws NullPointerException {
		super(context, R.layout.item_list_post, posts);

		this.context = context;
		this.posts = posts;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
			throws NullPointerException {

		View v = context.getLayoutInflater().inflate(R.layout.item_list_post,
				null);

		// Rescue the view
		usernamePost = (TextView) v.findViewById(R.id.usernamePost);
		datePost = (TextView) v.findViewById(R.id.datePost);
		descriptionPost = (TextView) v.findViewById(R.id.descriptionPost);
		userImage = (ImageView) v.findViewById(R.id.userImagePost);

		// Set data into view
		usernamePost.setText(posts.get(position).getUsername());
		descriptionPost.setText(posts.get(position).getDescription());
		datePost.setText(ConversionUtils.calculateDate(posts.get(position)
				.getDate()));

		ImageLoader imageLoader = new ImageLoader(context);

		imageLoader.clearFileCache();

		imageLoader.DisplayImageProfile(Constant.__BASEURL
				+ Constant.__USER_IMAGES + posts.get(position).getUsername()
				+ ".png", userImage);

		return v;
	}

}
