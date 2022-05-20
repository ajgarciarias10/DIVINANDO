// Generated by view binder compiler. Do not edit!
package com.divinando.tfg.adivinando.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.divinando.tfg.adivinando.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class NavHeaderMainBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final ImageView ivProfileIcon;

  @NonNull
  public final TextView tvProfileEmail;

  @NonNull
  public final TextView tvProfileName;

  private NavHeaderMainBinding(@NonNull LinearLayout rootView, @NonNull ImageView ivProfileIcon,
      @NonNull TextView tvProfileEmail, @NonNull TextView tvProfileName) {
    this.rootView = rootView;
    this.ivProfileIcon = ivProfileIcon;
    this.tvProfileEmail = tvProfileEmail;
    this.tvProfileName = tvProfileName;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static NavHeaderMainBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static NavHeaderMainBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.nav_header_main, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static NavHeaderMainBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.ivProfileIcon;
      ImageView ivProfileIcon = ViewBindings.findChildViewById(rootView, id);
      if (ivProfileIcon == null) {
        break missingId;
      }

      id = R.id.tvProfileEmail;
      TextView tvProfileEmail = ViewBindings.findChildViewById(rootView, id);
      if (tvProfileEmail == null) {
        break missingId;
      }

      id = R.id.tvProfileName;
      TextView tvProfileName = ViewBindings.findChildViewById(rootView, id);
      if (tvProfileName == null) {
        break missingId;
      }

      return new NavHeaderMainBinding((LinearLayout) rootView, ivProfileIcon, tvProfileEmail,
          tvProfileName);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
