package com.trdz.astro_days.view.segment_picture

import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.trdz.astro_days.R
import com.trdz.astro_days.view.MainActivity
import com.trdz.astro_days.view_model.MainViewModel
import com.trdz.astro_days.view_model.StatusProcess
import androidx.constraintlayout.widget.ConstraintLayout
import coil.request.ImageRequest
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.trdz.astro_days.databinding.FragmentWindowPofBinding
import com.trdz.astro_days.utility.*
import java.util.*
import kotlin.concurrent.thread
import android.graphics.BitmapFactory
import androidx.fragment.app.viewModels
import coil.clear
import coil.load
import com.trdz.astro_days.BuildConfig
import com.trdz.astro_days.view.scenes.SceneFlash
import com.trdz.astro_days.utility.KEY_PREFIX
import com.trdz.astro_days.utility.PREFIX_EPC
import com.trdz.astro_days.utility.PREFIX_MRP
import com.trdz.astro_days.utility.PREFIX_POD
import com.trdz.astro_days.view.Navigation
import com.trdz.astro_days.view_model.ViewModelFactories
import org.koin.android.ext.android.inject
import java.io.*
import java.net.HttpURLConnection
import java.net.URL


class WindowPictureOf: Fragment() {

	//region Injected
	private val navigation: Navigation by inject()
	private val factory: ViewModelFactories by inject()

	private val viewModel: MainViewModel by viewModels {
		factory
	}

	//endregion

	//region Elements
	private var _binding: FragmentWindowPofBinding? = null
	private val binding get() = _binding!!
	private var _bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>? = null
	private val bottomSheetBehavior get() = _bottomSheetBehavior!!
	private lateinit var prefix: String
	private var url: String? = null

	//endregion

	//region Base realization
	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		arguments?.let {
			prefix = it.getString(KEY_PREFIX, PREFIX_POD)
		}
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		_binding = FragmentWindowPofBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		val observer = Observer<StatusProcess> { renderData(it) }
		when (prefix) {
			PREFIX_POD -> viewModel.getPodData().observe(viewLifecycleOwner, observer)
			PREFIX_MRP -> viewModel.getPomData().observe(viewLifecycleOwner, observer)
			PREFIX_EPC -> viewModel.getPoeData().observe(viewLifecycleOwner, observer)
		}
		initialize()
	}

	//endregion

	//region Main functional

	/** Задание начального исполнения основного функционала*/
	private fun initialize() {
		buttonBinds()
		viewModel.initialize(prefix)
	}

	/** Настройка кнопок и взаимодействия с вспывающем окном*/
	private fun buttonBinds() {
		with(binding) {
			_bottomSheetBehavior = BottomSheetBehavior.from(popupSheet.bottomSheetContainer)
			imageView.setOnClickListener { bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED }
			bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
			bottomSheetBehavior.isHideable = true
			bottomSheetBehavior.addBottomSheetCallback(object:
				BottomSheetBehavior.BottomSheetCallback() {
				override fun onStateChanged(bottomSheet: View, newState: Int) {
					when (newState) {
						BottomSheetBehavior.STATE_DRAGGING -> {
						}
						BottomSheetBehavior.STATE_COLLAPSED -> {
							bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
						}
						BottomSheetBehavior.STATE_EXPANDED -> {
						}
						BottomSheetBehavior.STATE_HALF_EXPANDED -> {
						}
						BottomSheetBehavior.STATE_HIDDEN -> {
						}
						BottomSheetBehavior.STATE_SETTLING -> {
						}
					}
				}

				override fun onSlide(bottomSheet: View, slideOffset: Float) {}

			})
		}
	}

	/** Отображение данных полученных от VM */
	private fun renderData(material: StatusProcess) {
		when (material) {
			StatusProcess.Load -> {
				bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
			}
			is StatusProcess.Error -> {
				url = null
				Log.d("@@@", "App - $prefix catch $material.code")
				binding.imageView.clear()
				binding.imageView.setBackgroundResource(R.drawable.nofile)
				binding.popupSheet.title.text = getString(R.string.ERROR_TITLE)
				binding.popupSheet.explanation.text = StringBuilder(getString(R.string.ERROR_DISCRIPTIOn)).apply {
					append("\n")
					append(getString(R.string.Error_code_message))
					append(" ")
					append(material.code)
					append("\n")
					if (BuildConfig.NASA_API_KEY == "") append(getString(R.string.error_desc_m3))
					else when (material.code) {
						-2 -> append(getString(R.string.error_desc_m2))
						-1 -> append(getString(R.string.error_desc_m1))
						in 200..299 -> append(getString(R.string.error_desc_200))
						in 300..399 -> append(getString(R.string.error_desc_300))
						in 400..499 -> append(getString(R.string.error_desc_400))
						in 500..599 -> append(getString(R.string.error_desc_500))
						else -> append(getString(R.string.error_desc_0))
					}
				}
				bottomSheetBehavior.halfExpandedRatio = 0.35f
				bottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
			}
			is StatusProcess.Success -> {
				url = material.data.url
				Log.d("@@@", "App - $prefix render")
				with(binding) {
					imageView.setBackgroundResource(R.drawable.plaseholder)
					imageView.load(url) {
						listener(
							onSuccess = { _, _ ->
								// do nothing
							},
							onError = { request: ImageRequest, throwable: Throwable ->
								Log.d("@@@", "App - coil error $throwable")
								imageView.loadSvg(url!!) //если вдруг coil помрет
							})
					}
					popupSheet.title.text = material.data.name
					popupSheet.explanation.text = material.data.description
				}
			}
			is StatusProcess.Video -> {
				Log.d("@@@", "App - $prefix show")
			}
			is StatusProcess.Saving -> thread {
				navigation.add(requireActivity().supportFragmentManager, SceneFlash(),false,R.id.container_fragment_primal)
				galleryPic(material.data) }
		}
	}

	/** Сохранение картинки с окна на диск */
	private fun galleryPic(data: String) {
		Log.d("@@@", "App - Start saving image")
		val file = getDisc()
		if (!file.exists() && !file.mkdirs()) {
			Log.d("@@@", "App - Gallery not found");return
		}
		if (url==null) {
			Log.d("@@@", "App - Image don't exist");return
		}
		else url?.apply {
			val newFile = File("${file.absolutePath}/img$prefix$data.jpeg");
			try {
				val bitmap = getBitmapFromURL(this)
				val fOut = FileOutputStream(newFile)
				bitmap.compress(Bitmap.CompressFormat.PNG, 85, fOut)
				fOut.flush()
				fOut.close()
				Log.d("@@@", "App - Saving complete")
			}
			catch (ignored: FileNotFoundException) {
				Log.d("@@@T", "App - File corrupted")
			}
			catch (e: IOException) {
				Log.d("@@@", e.message.toString())
			}
		}

	}

	private fun getBitmapFromURL(src: String): Bitmap {
		val url = URL(src)
		val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
		connection.doInput = true
		connection.connect()
		val input: InputStream = connection.inputStream
		return BitmapFactory.decodeStream(input)
	}

	private fun getDisc()= File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Picture of my Day")

	//endregion

	companion object {
		@JvmStatic
		fun newInstance(prefix: String) =
			WindowPictureOf().apply {
				arguments = Bundle().apply {
					putString(KEY_PREFIX, prefix)
				}
			}
	}

}