package org.fengye.school.ui.fragment;


import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.qmuiteam.qmui.widget.dialog.QMUIDialog;

import org.fengye.school.R;
import org.fengye.school.base.BaseFragment;
import org.fengye.school.databinding.FragmentKnowBinding;
import org.fengye.school.model.bmob.Thesaurus;
import org.fengye.school.model.sqlite.LearnPlan;
import org.fengye.school.model.sqlite.TodayWord;
import org.fengye.school.vm.KnowViewModel;
import org.fengye.school.vm.ViewStatus;

import java.util.ArrayList;
import java.util.List;

import permissions.dispatcher.RuntimePermissions;

/**
 * A simple {@link Fragment} subclass.
 */
public class KnowFragment extends BaseFragment<FragmentKnowBinding, KnowViewModel> {

    private MediaPlayer mediaPlayer;

    public static KnowFragment newInstance() {

        Bundle args = new Bundle();

        KnowFragment fragment = new KnowFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public KnowFragment() {
    }

    @Override
    protected void initData() {
        super.initData();
        viewModel.refreshTodaySent();

        viewModel.definePlan();

    }

    private void initMediaPlayer() {

        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(viewModel.getTodaySent().getValue().getTts());
            mediaPlayer.prepareAsync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void initView() {
        super.initView();
        binding.topBar.setTitle("知识");

        ViewModelProvider.Factory factory = new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new KnowViewModel();
            }
        };

        binding.setViewModel(viewModel);
    }

    @Override
    protected void initListener() {
        super.initListener();
        viewModel.getTodaySent().observe(this, new Observer<TodayWord>() {
            @Override
            public void onChanged(TodayWord todayWord) {
                binding.setTodayWord(todayWord);
                initMediaPlayer();
            }
        });


        viewModel.getThesaurus().observe(this, new Observer<List<Thesaurus>>() {
            @Override
            public void onChanged(final List<Thesaurus> thesauruses) {

                int selected = -1;
                String[] items = new String[thesauruses.size()];
                for (int i = 0; i < thesauruses.size(); i++) {
                    items[i] = thesauruses.get(i).getName() + "(" + thesauruses.get(i).getTotal() + ")";
                    LearnPlan learnPlan = viewModel.studyPlanField.get();
                    if (learnPlan != null && thesauruses.get(i).getObjectId().equals(learnPlan.getPlanId())) {
                        selected = i;
                    }
                }
                new QMUIDialog.CheckableDialogBuilder(getActivity())
                        .setCheckedIndex(selected)
                        .addItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Thesaurus thesaurus = thesauruses.get(which);

                                dialog.dismiss();
                                viewModel.downThesaurus(thesaurus);
                            }
                        })
                        .create().show();
            }
        });

        viewModel.getViewStatus().observe(this, new Observer<ViewStatus>() {
            @Override
            public void onChanged(ViewStatus viewStatus) {
                if (viewStatus == ViewStatus.LOADING) {
                    tips();
                } else if (viewStatus == ViewStatus.IDLE) {
                    cancelTip();
                }
            }
        });


        binding.playSentence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSentence();
            }
        });
        binding.startWords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewModel.getThesaurus().getValue() == null) {
                toast("请先选择学习计划");
                return;
            }
                startFragment(RememberWordFragment.newInstance());
            }
        });

        binding.resetPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.showThesaurus();
//                List<Thesaurus> thesauruses = new ArrayList<>();
//                thesauruses.add(new Thesaurus());
//                viewModel.getThesaurus().setValue(thesauruses);
            }
        });

        binding.allLearnWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewModel.getThesaurus().getValue() == null) {
                    toast("请先选择学习计划");
                    return;
                }
                startFragment(WordRecordFragment.newInstance(0));
            }
        });

        binding.todayWords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewModel.getThesaurus().getValue() == null) {
                    toast("请先选择学习计划");
                    return;
                }
                startFragment(WordRecordFragment.newInstance(1));
            }
        });

        binding.todayReadyLearn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewModel.getThesaurus().getValue() == null) {
                    toast("请先选择学习计划");
                    return;
                }
                startFragment(WordRecordFragment.newInstance(2));
            }
        });

        binding.notKnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewModel.getThesaurus().getValue() == null) {
                    toast("请先选择学习计划");
                    return;
                }
                startFragment(WordRecordFragment.newInstance(3));
            }
        });

    }

    private void playSentence() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            } else {
                mediaPlayer.seekTo(0);
                mediaPlayer.start();
            }
        }
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_know;
    }

    @Override
    protected KnowViewModel createViewModel() {
        return ViewModelProviders.of(getActivity(), viewModelFactory).get(KnowViewModel.class);
    }
}
