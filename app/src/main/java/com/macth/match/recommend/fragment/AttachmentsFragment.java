package com.macth.match.recommend.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.macth.match.AppConfig;
import com.macth.match.AppContext;
import com.macth.match.R;
import com.macth.match.common.base.BasePullScrollViewFragment;
import com.macth.match.common.http.CallBack;
import com.macth.match.common.http.CommonApiClient;
import com.macth.match.common.utils.LogUtils;
import com.macth.match.common.utils.OpenFileUtils;
import com.macth.match.common.utils.StringUtils;
import com.macth.match.common.widget.EmptyLayout;
import com.macth.match.common.widget.FullyLinearLayoutManager;
import com.macth.match.recommend.entity.AttachmentsDTO;
import com.macth.match.recommend.entity.AttachmentsEntity;
import com.macth.match.recommend.entity.AttachmentsResult;
import com.qluxstory.ptrrecyclerview.BaseRecyclerAdapter;
import com.qluxstory.ptrrecyclerview.BaseRecyclerViewHolder;
import com.qluxstory.ptrrecyclerview.BaseSimpleRecyclerAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import butterknife.Bind;

/**
 * 查看附件
 */
public class AttachmentsFragment extends BasePullScrollViewFragment {
    @Bind(R.id.attachments_list)
    RecyclerView mAttachmentsList;
    BaseSimpleRecyclerAdapter mAdapter;
    private String mMfileid;

    List<AttachmentsEntity> data;

    private ProgressDialog dialog;
    private File apkFile;

    String attachentFlag; //用于标记是下载点击还是查看
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_attachments;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        Bundle bundle = getArguments();
        mMfileid = bundle.getString("mfileid");
        mAttachmentsList.setLayoutManager(new FullyLinearLayoutManager(getActivity()));
        mAdapter = new BaseSimpleRecyclerAdapter<AttachmentsEntity>() {
            TextView attach_tv2;

            @Override
            public int getItemViewLayoutId() {
                return R.layout.item_attachments;
            }

            @Override
            public void bindData(BaseRecyclerViewHolder holder, AttachmentsEntity attachmentsEntity, final int position) {

                holder.setText(R.id.attach_tv1, StringUtils.toUtf8(attachmentsEntity.getName()));
                //判断该文件名是否存在，如果不存在，就显示下载，如果存在就显示查看
                attach_tv2 = holder.getView(R.id.attach_tv2);
                final String attachentPath = AppContext.get(attachmentsEntity.getPath(), "");

                 attachentFlag = attachmentsEntity.getPath() + 01;

                if (!TextUtils.isEmpty(attachentPath)) {

                    LogUtils.d("附件的本地路径存在=========" + attachentPath);
                    attach_tv2.setText("查看");
//                    attach_tv2.setTag();
                    AppContext.set(attachentFlag, "chakan");


                } else {
                    LogUtils.d("附件的本地路径不存在=========");
                    attach_tv2.setText("下载");
//                    attach_tv2.setTag(2);
                    AppContext.set(attachentFlag, "xiazai");
                }


                attach_tv2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        
                        
                        if(!TextUtils.isEmpty(attachentPath)) {
                            LogUtils.i("查看操作");
                            OpenFileUtils openFileUtils = new OpenFileUtils(getActivity());
                            File file = new File(attachentPath);
                            openFileUtils.openFile(file);
                            
                        }else {
                            LogUtils.i("下载操作");
//                            String path = StringUtils.toUtf8(data.get(position).getPath());
                            String path = data.get(position).getPath();
                            LogUtils.d("下载附件的url=========" + path);

                            loading();
//                            createFile(StringUtils.toUtf8(data.get(position).getName()));
                            createFile(data.get(position).getName());
                            download(path);

                        }
//
//                        if ("chakan".equals(AppContext.get(attachentFlag, ""))) {  //查看
//                            LogUtils.i("查看操作");
//                            OpenFileUtils openFileUtils = new OpenFileUtils(getActivity());
//                            File file = new File(attachentPath);
//                            openFileUtils.openFile(file);
//
//                        } else {    //下载
//                            LogUtils.i("下载操作");
////                            String path = StringUtils.toUtf8(data.get(position).getPath());
//                            String path = data.get(position).getPath();
//                            LogUtils.d("下载附件的url=========" + path);
//
//                            loading();
////                            createFile(StringUtils.toUtf8(data.get(position).getName()));
//                            createFile(data.get(position).getName());
//                            download(path);
//                        }

                    }
                });
            }
        };


        mAttachmentsList.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View itemView, Object itemBean, int position) {

            }
        });

    }

    @Override
    public void initData() {
        reqAttachments();
    }

    private void reqAttachments() {
        AttachmentsDTO dto = new AttachmentsDTO();
        dto.setMfileid(mMfileid);
        CommonApiClient.attachments(getActivity(), dto, new CallBack<AttachmentsResult>() {
            @Override
            public void onSuccess(AttachmentsResult result) {
                if (AppConfig.SUCCESS.equals(result.getCode())) {
                    LogUtils.e("查看附件成功");
                    mErrorLayout.setErrorMessage("暂无附件记录", mErrorLayout.FLAG_NODATA);
                    mErrorLayout.setErrorImag(R.drawable.page_icon_empty, mErrorLayout.FLAG_NODATA);
                    if (null == result.getData()) {
                        mErrorLayout.setErrorType(EmptyLayout.NODATA);
                    } else {
                        mAdapter.removeAll();
                        mAdapter.append(result.getData());
//                        refreshComplete();
                        data = result.getData();
//                        LogUtils.d("附件0==========="+attachUrl);
                    }
                }

            }
        });
    }

//    Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
////            super.handleMessage(msg);
//            switch (msg.what) {
//                case  0:
//                    Toast.makeText(getActivity(), "下载完成", Toast.LENGTH_SHORT).show();
//                    //设置dialog消失
//                    dialog.dismiss();
//                    //修改ui为查看
//                    mAdapter.notify();
//
//                    break;
//            }
//        }
//    };


    public void download(final String path) {

        new Thread() {
            public void run() {
                HttpURLConnection conn = null;
                InputStream is = null;
                FileOutputStream fos = null;
                try {
                    URL url = new URL(path);
                    conn = (HttpURLConnection) url.openConnection();

                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(5000);
                    conn.setReadTimeout(5000);

                    conn.connect();

                    int responseCode = conn.getResponseCode();
                    //将返回数据的长度设置为dialog的最大值
                    dialog.setMax(conn.getContentLength());

                    if (responseCode == 200) {
                        is = conn.getInputStream();
                        fos = new FileOutputStream(apkFile);

                        byte[] buffer = new byte[1024];
                        int len;
                        while ((len = is.read(buffer)) != -1) {
                            fos.write(buffer, 0, len);
                            //方式一：
//							dialog.setProgress(dialog.getProgress() + len);
                            //方式二：
                            dialog.incrementProgressBy(len);

//                            SystemClock.sleep(20);
                        }

                        //把apkFile的路径保存在本地
                        AppContext.set(path, apkFile.getAbsolutePath());
                        LogUtils.d("把apkFile的路径保存在本地,," + path + "====" + apkFile.getAbsolutePath());
                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(getActivity(), "下载完成", Toast.LENGTH_SHORT).show();
                                //设置dialog消失
                                dialog.dismiss();
                                //修改ui为查看？？？？？
                                AppContext.set(attachentFlag, "chakan");
                                LogUtils.d("downAndSee==========下载完后，" + AppContext.get(attachentFlag, ""));
                                reqAttachments();
                            }
                        });

                    } else {
                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(getActivity(), "资源找不到", Toast.LENGTH_SHORT).show();
                                //设置dialog消失
                                dialog.dismiss();
                            }
                        });

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getActivity(), "连接出现异常", Toast.LENGTH_SHORT).show();
                            //设置dialog消失
                            dialog.dismiss();
                        }
                    });
                } finally {
                    conn.disconnect();

                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }.start();
    }

    //2.生成要保存到的文件
    public void createFile(String name) {
        File filesDir;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            filesDir = getActivity().getExternalFilesDir(null);//使用sd卡存储
        } else {
            filesDir = getActivity().getFilesDir();//使用手机内部存储
        }
        apkFile = new File(filesDir, name);
        LogUtils.d("apkFile22222222222222=========" + apkFile);
    }

    //1.生成一个ProgressDialog
    public void loading() {
        dialog = new ProgressDialog(getContext());
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setCancelable(false);
        dialog.show();
    }
}
